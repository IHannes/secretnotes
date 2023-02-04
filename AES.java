import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.swing.JOptionPane;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.KeyStore.SecretKeyEntry;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Scanner;


public class AES {
    private static final String password = "einszwei";
    public static void main(String[] args) throws Exception{
        AES ln = new AES();
        SecretKey key = ln.loadKey("/home/hanhil/projects/secno/keystore.pfx");
        System.out.println(key.toString());
        byte[] encryptedBytes = ln.encrypt(key, "test");
        ln.filestore(encryptedBytes, "/home/hanhil/encb.txt");
        byte[] encB = ln.fileload("/home/hanhil/encb.txt");
        String result = ln.decrypt(encB, key);
        System.out.println(result);
        }

        /*public SecretKey genKey() throws Exception{
            KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(192);
        SecretKey key = generator.generateKey();
        return key;
        }*/
    public byte[] encrypt(SecretKey key, String Message) throws Exception{
        

        byte[] input = Message.getBytes();
        System.out.println(new String(input));
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(input);
        System.out.println(new String(encryptedBytes));
        return encryptedBytes;

    }

    public String decrypt(byte[] encB, Key key) throws Exception{
        Cipher ciphe = Cipher.getInstance("AES/ECB/PKCS5Padding");
        ciphe.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = ciphe.doFinal(encB);
        //System.out.println(new String(encB));
        //System.out.println(new String(decryptedBytes));
        return new String(decryptedBytes);
    }
    public void filestore(byte[] inp, String path) throws Exception{
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        String input = Base64.getEncoder().encodeToString(inp);
        //System.out.println(input);
        new FileWriter(path, false).close();
        fw.write(input);
        fw.close();
    }
    public byte[] fileload(String path) throws Exception{
        File file = new File(path);
        Scanner myReader = new Scanner(file);
        String enc = new String();
        while (myReader.hasNextLine()) {
          enc = myReader.nextLine();}
        byte[] decode = Base64.getDecoder().decode(enc);
        //System.out.println(enc);
        return decode;
    }
    /*public void storeKey(String path, SecretKey key) throws Exception{
        File file = new File(path);
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(file);
        ks.load(fis, password.toCharArray());
        KeyStore.PasswordProtection protectionParam = new KeyStore.PasswordProtection(password.toCharArray());
        if (!ks.containsAlias("hannes")) {
        KeyStore.SecretKeyEntry entry= new KeyStore.SecretKeyEntry(key);
        ks.setEntry("hannes", entry, protectionParam);
        }
    }*/
    public SecretKey loadKey(String path) throws Exception{
        File file = new File(path);
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(file);
        ks.load(fis, password.toCharArray());
        KeyStore.PasswordProtection protectionParam = new KeyStore.PasswordProtection(password.toCharArray());
        System.out.println( ks.containsAlias("hannes"));
        KeyStore.Entry entry = ks.getEntry("hannes", protectionParam);
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) entry;
        Key key = secretKeyEntry.getSecretKey();
        return (SecretKey) key;
    }
}