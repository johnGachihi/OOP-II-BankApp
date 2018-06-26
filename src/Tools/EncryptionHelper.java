package Tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class EncryptionHelper {

    public static EncryptedPin get_SHA256(String passwordToHash){
        byte[] salt = getSalt();
        String encPin = get_SHA256(passwordToHash, salt);
        return new EncryptedPin(encPin, toHexString(salt));
    }

    public static String get_SHA256(String passwordToHash, byte[] salt){
        String encPassword = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            encPassword = toHexString(bytes);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return encPassword;
    }

    public static byte[] getSalt(){
        byte[] salt = new byte[16];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return salt;
    }

    private static String toHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
            sb.append(Integer.toString(
                    (bytes[i] & 0xff) + 0x100, 16).substring(1));
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static class EncryptedPin{
        private String encPin, salt;

        public EncryptedPin(String encPin, String salt) {
            this.encPin = encPin;
            this.salt = salt;
        }

        public String getEncPin() {
            return encPin;
        }

        public String getUsedSalt() {
            return salt;
        }
    }
}
