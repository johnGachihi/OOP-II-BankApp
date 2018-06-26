package pages;

import Tools.Authenticator;
import Tools.DBHelper;
import Tools.PreferencesHelper;
import Tools.StageSetter;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import pojos.Account;
import rmiServer.TransactionManager;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.prefs.Preferences;

public class HomePage implements StageSetter, Initializable{

    public Label lblAccountNumber;
    public Label lblBalance;
    private Stage primaryStage;
    Account curAccount;
    Preferences prefs;
    TransactionManager transactionManager;

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        prefs = Preferences.userNodeForPackage(AccountCreate.class);
        byte[] buf = prefs.getByteArray("current_account", null);

        curAccount = (Account) PreferencesHelper.byteToObject(buf);
        System.out.println(curAccount.getFirstName());
        transactionManager = new TransactionManager(curAccount);

        //Account number will be set in the transaction manager
        //The below call to DBHelper will be insignificant
        curAccount.setAccNumber(DBHelper.getAccountNumber(
                curAccount.getEncPin(), curAccount.getSalt()));

        lblAccountNumber.setText("Account Number: " + curAccount.getAccNumber());
        setLblBalanceText(curAccount.getBalance());
    }

    public void deposit(){
        Pair<String, String> pair = showDepositOrWithdrawDialog("Deposit");
        if(pair != null){
            if(Authenticator.credentialsValid(pair.getKey())){
                //DBHelper.deposit will be replaced by:
                // TransactionManager.makeDeposit(Float.parseFloat(pair.getValue()))
                setLblBalanceText(transactionManager.makeDeposit(Float.parseFloat(pair.getValue())));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Credentials incorrect");
                alert.show();
            }
        }
    }

    public void withdraw(){
        Pair<String, String> pair = showDepositOrWithdrawDialog("Withdrawal");
        if(pair != null){
            if(Authenticator.credentialsValid(pair.getKey())){
                setLblBalanceText(transactionManager.makeWithdrawal(Float.parseFloat(pair.getValue())));
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Credentials incorrect");
                alert.show();
            }
        }
                }

    private void setLblBalanceText(float balance){
        lblBalance.setText("Balance: KSH. " + balance);
    }

    private Pair<String, String> showDepositOrWithdrawDialog(String depositOrWithdrawal){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle(depositOrWithdrawal);
        dialog.setHeaderText("Make " + depositOrWithdrawal);

        ButtonType btnDeposit = new ButtonType(depositOrWithdrawal, ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnDeposit, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField passtxtfield = new PasswordField();
        passtxtfield.setPromptText("Pin");
        TextField txtAmount = new TextField();
        txtAmount.setPromptText("Amount");

        grid.add(new Label("Pin: "), 0, 0);
        grid.add(passtxtfield, 1, 0);
        grid.add(new Label("Amount: "), 0, 1);
        grid.add(txtAmount, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(new Callback<ButtonType, Pair<String, String>>() {
            @Override
            public Pair<String, String> call(ButtonType param) {
                if(param == btnDeposit){
                    return new Pair<>(passtxtfield.getText(), txtAmount.getText());
                }
                return null;
            }
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(new Consumer<Pair<String, String>>() {
            @Override
            public void accept(Pair<String, String> stringStringPair) {
                System.out.println("Pin " + stringStringPair.getKey());
                System.out.println("Amount" + stringStringPair.getValue());
            }
        });
        return result.get();
    }

}
