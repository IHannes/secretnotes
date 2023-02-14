import java.security.Key;
import java.util.Base64;

import javax.crypto.SecretKey;

public class encdec{
    public String encrypt(String input, String password){
        maxsec maxi = new maxsec();
        String longPassword = maxi.longPassword(password, input);
        int[] pwPosition = maxi.passwordPosition(longPassword);
        int[] tBEPosition = maxi.tBEPosition(input);
        int[] sums = maxi.sums(pwPosition, tBEPosition);
        String encrypted = maxi.encrypted(sums);
        int[] encryptedPosition = maxi.encryptedPosition(encrypted);
        String decrypted = maxi.decrypted(encryptedPosition, pwPosition);
        String[] randomStringArray = maxi.generateArrayOfRandomStrings(encrypted.length()+1, pwPosition);
        String reencrypted = maxi.reencrypt(encrypted, randomStringArray, input.length());
        String redecrypted = maxi.redecrypt(reencrypted, pwPosition);
        int[] enP = maxi.encryptedPosition(redecrypted);
        String res1 = maxi.decrypted(enP, pwPosition);
        
        AES a = new AES();
        try {
            SecretKey key = a.loadKey("/home/hanhil/projects/secno/keystore.pfx");
            byte[] encryptedBytes = a.encrypt(key, reencrypted);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println(e);
        }
        return "error";
    }
    public String decrypt(String input, String password) {
        StringBuilder res  = new StringBuilder("");
        try {
            AES a = new AES();
            SecretKey key = a.loadKey("/home/hanhil/projects/secno/keystore.pfx");
            res.append(a.decrypt(Base64.getDecoder().decode(input), key));
        } catch (Exception e) {
            System.err.println(e);
        }
        int length = Character.getNumericValue(res.charAt(res.length()-1));
        maxsec m = new maxsec();
        res.delete(res.length()-1, res.length());
        String longPassword = m.longPassword(password, length);
        int[] pwPosition = m.passwordPosition(longPassword);
        String redecrypted = m.redecrypt(res.toString(), pwPosition);
        int[] enP = m.encryptedPosition(redecrypted);
        String result = m.decrypted(enP, pwPosition);
        return result;
    }
}
