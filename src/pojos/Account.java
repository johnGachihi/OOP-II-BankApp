package pojos;

import Tools.EncryptionHelper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Serializable;

public class Account implements Serializable{

    private int accNumber;
    private String encPin;
    private String salt;
    private float balance;
    private String firstName;
    private String lastName;

    public Account(String pin, String firstName, String lastName){
        EncryptionHelper.EncryptedPin ep = EncryptionHelper.get_SHA256(pin);
        this.encPin = ep.getEncPin();
        this.salt = ep.getUsedSalt();

        balance = 0;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /*public static void main(String[] args){
        *//*Account account = new Account("1234", "John", "Gachihi");
        System.out.println(account.getSalt());
        System.out.println(account.getEncPin());
        System.out.println(EncryptionHelper.get_SHA256("1234", EncryptionHelper.hexStringToByteArray(account.getSalt())));*//*

    }*/


    public int getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(int accNumber) {
        this.accNumber = accNumber;
    }

    public String getEncPin() {
        return encPin;
    }

    public String getSalt() {
        return salt;
    }

    public float getBalance() {
        return balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void deposit(float amount){
        //add to instance variable <- or get updated db value
        //then to database
    }
}
