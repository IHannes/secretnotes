import java.security.Key;
import java.util.Base64;

import javax.crypto.SecretKey;

public class encdec{
    public String encrypt(String input, String fileName, String password){
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
            SecretKey key = a.loadKey(fileName, password);
            byte[] encryptedBytes = a.encrypt(key, reencrypted, password);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.err.println(e);
        }
        return "error";
    }
    public String decrypt(String input, String fileName, String password) {
        StringBuilder res  = new StringBuilder("");
        try {
            AES a = new AES();
            SecretKey key = a.loadKey(fileName, password);
            res.append(a.decrypt(Base64.getDecoder().decode(input), key));
        } catch (Exception e) {
            System.err.println(e);
        }
        int length = getLength(res);
        int pos = getPos(res);
        maxsec m = new maxsec();
        res.delete(pos, res.length());
        String longPassword = m.longPassword(password, length);
        int[] pwPosition = m.passwordPosition(longPassword);
        String redecrypted = m.redecrypt(res.toString(), pwPosition);
        int[] enP = m.encryptedPosition(redecrypted);
        String result = m.decrypted(enP, pwPosition);
        return result;
    }
    private static int getLength(StringBuilder res){
        StringBuilder strb = new StringBuilder("");
        for(int i = 0; i<res.length(); i++){
            if(res.charAt(i)=='|' && res.charAt(i+1)=='|' && res.charAt(i+2)=='|' && res.charAt(i+3)=='|'){
                strb.append(res.substring(i+4, res.length()));
                break;
            }
        }
        return Integer.parseInt(strb.toString());
    }
    private static int getPos(StringBuilder res){
        for(int i = 0; i<res.length(); i++){
            if(res.charAt(i)=='|' && res.charAt(i+1)=='|' && res.charAt(i+2)=='|' && res.charAt(i+3)=='|'){
                return i;
            }
        }
        return -1;
    }
}
