package pages;

import Tools.DBHelper;
import Tools.PreferencesHelper;
import Tools.StageSetter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pojos.Account;

import java.awt.*;
import java.io.IOException;
import java.util.prefs.Preferences;

public class AccountCreate implements StageSetter {

    public TextField txtFirstName;
    public TextField txtLastName;
    public PasswordField ptxtPin;

    private Stage primaryStage;

    public void onSubmitClicked(){
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String pin = ptxtPin.getText();

        if(!firstName.isEmpty() &&
                !lastName.isEmpty() &&
                !pin.isEmpty())
        {
            Account account = new Account(pin, firstName, lastName);
            DBHelper.createAccount(account);
            Preferences prefs = Preferences.userNodeForPackage(AccountCreate.class);
            prefs.putByteArray("current_account", PreferencesHelper.objectToBytes(account));
            openHomePage();
        }
    }

    public void openHomePage(){
        try{
            FXMLLoader homePageLoader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Parent homePage = homePageLoader.load();

            StageSetter stageSetter = homePageLoader.getController();
            stageSetter.setStage(primaryStage);

            primaryStage.setScene(new Scene(homePage));
            primaryStage.show();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*public void showAccountNumberDialog(String accNo){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account Number");
        alert.setHeaderText(accNo + "\nThe above is your account number " +
                "You will use it to access this account.");
        alert.showAndWait();
    }*/

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
