package Tools;

import java.io.*;

public class PreferencesHelper {

    public static byte[] objectToBytes(Object o){
        ByteArrayOutputStream baos = null;
        try{
            baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
        }catch (IOException e){
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static Object byteToObject(byte[] b){
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        Object o = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            o = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return  o;
    }
}
