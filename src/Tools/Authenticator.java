package Tools;

import pages.AccountCreate;
import pojos.Account;

import java.util.prefs.Preferences;

public class Authenticator {

    static Preferences prefs;

    /*public static void main(String[] args){
        if(pinIsValid(123, "1234"))
            System.out.println("Yesss");
        else
            System.out.println("Nooo");
    }*/

   /* public static boolean credentialsValid(String pin){
        prefs = Preferences.userNodeForPackage(AccountCreate.class);
        Account account = (Account) PreferencesHelper.byteToObject(
                prefs.getByteArray("current_account", null));
    }*/

    public static boolean credentialsValid(String pin){
        //get salt and encPin from preferences
        //encrypt param Pin using salt
        //compare result
        prefs = Preferences.userNodeForPackage(AccountCreate.class);
        Account account = (Account) PreferencesHelper.byteToObject(
                prefs.getByteArray("current_account", null));

        byte[] salt = EncryptionHelper.hexStringToByteArray(account.getSalt());
        if(EncryptionHelper.get_SHA256(pin, salt).equals(account.getEncPin())){
            return true;
        } else
            return false;
    }
}
