import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import java.util.Scanner;


public class AES {
    //private static final String password = "einszwei";
    private static final String pth = System.getProperty("user.dir");
       
    public byte[] encrypt(SecretKey key, String Message, String password) throws Exception{
        byte[] input = Message.getBytes();
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(input);
        return encryptedBytes;

    }

    private static String generateKeystorePath(){
        StringBuilder strb = new StringBuilder(pth);
        strb.append("/notes.pfx");
        return strb.toString();
    }

    public String decrypt(byte[] encB, Key key) throws Exception{
        Cipher ciphe = Cipher.getInstance("AES/ECB/PKCS5Padding");
        ciphe.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = ciphe.doFinal(encB);
        return new String(decryptedBytes);
    }
    public void filestore(byte[] inp, String path) throws Exception{
        File file = new File(path);
        FileWriter fw = new FileWriter(file);
        String input = Base64.getEncoder().encodeToString(inp);
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
        return decode;
    }
    public SecretKey loadKey(String name, String password) throws Exception{
        File file = new File(generateKeystorePath());
        KeyStore ks = KeyStore.getInstance("PKCS12");
        FileInputStream fis = new FileInputStream(file);
        ks.load(fis, password.toCharArray());
        KeyStore.PasswordProtection protectionParam = new KeyStore.PasswordProtection(password.toCharArray());
        KeyStore.Entry entry = ks.getEntry(name, protectionParam);
        KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) entry;
        Key key = secretKeyEntry.getSecretKey();
        return (SecretKey) key;
    }
}