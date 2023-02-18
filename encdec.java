import java.security.Key;
import java.util.Base64;

import javax.crypto.SecretKey;

public class encdec{
    public String encrypt(String input, String password){
        maxsec maxi = new maxsec();
        String longPassword = maxi.longPassword(password, input);
        System.out.println("lp: " + longPassword + "\n\n");
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
        System.out.println("res: " + res + "\n\n");
        int length = getLength(res);
        int pos = getPos(res);
        System.out.println("lenghth: " + length + "\n\n");
        maxsec m = new maxsec();
        res.delete(pos, res.length());
        String longPassword = m.longPassword(password, length);
        System.out.println("lp: " + longPassword + "\n\n");
        int[] pwPosition = m.passwordPosition(longPassword);
        System.out.println(res + "\n\n");
        String redecrypted = m.redecrypt(res.toString(), pwPosition);
        System.out.println(redecrypted);
        int[] enP = m.encryptedPosition(redecrypted);
        System.out.println("pwp: " + pwPosition.length);
        System.out.println("encp: " + enP.length);
        String result = m.decrypted(enP, pwPosition);
        return result;
    }
    private static int getLength(StringBuilder res){
        StringBuilder strb = new StringBuilder("");
        for(int i = 0; i<res.length(); i++){
            if(res.charAt(i)=='|' && res.charAt(i+1)=='|' && res.charAt(i+2)=='|' && res.charAt(i+3)=='|'){
                System.out.println("success: " + res.substring(i+4, res.length()));
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
