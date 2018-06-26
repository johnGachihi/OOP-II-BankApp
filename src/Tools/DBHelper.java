package Tools;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import pojos.Account;

import javax.xml.transform.Result;
import java.sql.*;

public class DBHelper {

    private static final String URL = "jdbc:mysql://localhost/bankapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    private static void getConnection(){
        boolean unableToConnect = true;
        do{
            try{
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connection established");
                unableToConnect = false;
            } catch (SQLException e){
                e.printStackTrace();
                showUnableToConnectErrorDialog();
            }
        } while (unableToConnect);
    }

    public static void createAccount(Account account){
        if(connection == null)
            getConnection();

        try{
            PreparedStatement prpStmt = connection.prepareStatement(
                    "INSERT INTO " + BankAppDB.TABLE_ACCOUNTS + "(" +
                            BankAppDB.AccountsTable.ACC_PIN + ", " +
                            BankAppDB.AccountsTable.ACC_BALANCE + ", " +
                            BankAppDB.AccountsTable.ACC_FIRSTNAME + ", " +
                            BankAppDB.AccountsTable.ACC_LASTNAME + ", " +
                            BankAppDB.AccountsTable.ACC_SALT +
                            ") " +
                            " VALUES (?, ?, ?, ?, ?);"
            );
            //prpStmt.setInt(1, account.getAccNumber());
            prpStmt.setString(1, account.getEncPin());
            prpStmt.setFloat(2, account.getBalance());
            prpStmt.setString(3, account.getFirstName());
            prpStmt.setString(4, account.getLastName());
            prpStmt.setString(5, account.getSalt());

            prpStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //This is a hack. Better means required.
    public static int getAccountNumber(String encPin, String salt){
        if(connection == null)
            getConnection();

        int accountNumber = 0;
        try{
            PreparedStatement prpStmt = connection.prepareStatement(
                    "SELECT * FROM " + BankAppDB.TABLE_ACCOUNTS +
                            " WHERE " + BankAppDB.AccountsTable.ACC_PIN + " = ? AND " +
                            BankAppDB.AccountsTable.ACC_SALT + " = ?"
            );
            prpStmt.setString(1, encPin);
            prpStmt.setString(2, salt);
            ResultSet rs = prpStmt.executeQuery();
            rs.next();
            accountNumber = rs.getInt("accountNo");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return accountNumber;
    }

    //Returns updated balance
    public static float deposit(float amount, int accountNo){
        if(connection == null)
            getConnection();

        try{
            PreparedStatement prpStmt = connection.prepareStatement(
                    "UPDATE " + BankAppDB.TABLE_ACCOUNTS +
                            " SET " + BankAppDB.AccountsTable.ACC_BALANCE +
                            " = " + BankAppDB.AccountsTable.ACC_BALANCE + " + ?" +
                            " WHERE " + BankAppDB.AccountsTable.ACC_NUMBER + " = ?"
            );
            prpStmt.setFloat(1, amount);
            prpStmt.setInt(2, accountNo);
            prpStmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return getBalance(accountNo);
    }

    public static float withdraw(float amount, int accountNo){
        if(connection == null)
            getConnection();

        try{
            PreparedStatement prpStmt = connection.prepareStatement(
                    "UPDATE " + BankAppDB.TABLE_ACCOUNTS +
                            " SET " + BankAppDB.AccountsTable.ACC_BALANCE +
                            " = " + BankAppDB.AccountsTable.ACC_BALANCE + " - ?" +
                            " WHERE " + BankAppDB.AccountsTable.ACC_NUMBER + " = ?"
            );
            prpStmt.setFloat(1, amount);
            prpStmt.setInt(2, accountNo);
            prpStmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return getBalance(accountNo);
    }

    private static float getBalance(int accountNo){
        float balance = -1;
        try{
            PreparedStatement prpStmt = connection.prepareStatement(
                    "SELECT " + BankAppDB.AccountsTable.ACC_BALANCE +
                            " FROM " + BankAppDB.TABLE_ACCOUNTS +
                            " WHERE " + BankAppDB.AccountsTable.ACC_NUMBER + "= ?"
            );
            prpStmt.setInt(1, accountNo);
            ResultSet rs = prpStmt.executeQuery();
            rs.next();
            balance = rs.getFloat("balance");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return balance;
    }

    private static void showUnableToConnectErrorDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Error");
        alert.setHeaderText("Unable to establish a connection.");
        alert.setContentText("Check connection the Retry");

        ButtonType btnRetry = new ButtonType("Retry", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().add(btnRetry);

        alert.showAndWait();
    }
}
