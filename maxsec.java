import java.security.SecureRandom;
import java.util.Arrays;
//import java.util.stream.IntStream;
import java.util.List;

import javax.swing.JOptionPane;

//TODO: plus-plus-modulo_length


public class maxsec {
    static char[] elements = {' ', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'ß', '?', '=', ')', '(', '/', '&', '%', '$', '§', '"', '!', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '<', '>', '|', ',', ';', '.', ':', '-', '_', '*', '+', '~', '#', '\'', '^', '°', '¹', '²', '³', '¼', '½', '¬', '{', '[', ']', '}', '\\', '´', '`', '\n', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    //char[] elements = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'};
    public static void main(String[] args) {
        String password = JOptionPane.showInputDialog(null, "put in password");
        String toBeEncrypted = JOptionPane.showInputDialog(null, "what would you like to encrypt?");
        maxsec maxsec = new maxsec();
        String longPassword = maxsec.longPassword(password, toBeEncrypted);
        System.out.println(longPassword);
        int[] pwPosition = maxsec.passwordPosition(longPassword);
        int[] tBEPosition = maxsec.tBEPosition(toBeEncrypted);
        int[] sums = maxsec.sums(pwPosition, tBEPosition);
        for (int i : sums) {
            System.out.println("sum" + i);
        }
        String encrypted = maxsec.encrypted(sums);
        System.out.println("Enc+ " + encrypted);
        int[] encryptedPosition = maxsec.encryptedPosition(encrypted);
        String decrypted = maxsec.decrypted(encryptedPosition, pwPosition);
        System.out.println(decrypted);

        String[] randomStringArray = maxsec.generateArrayOfRandomStrings(encrypted.length()+1, pwPosition);
        String reencrypted = maxsec.reencrypt(encrypted, randomStringArray);
        System.out.println(reencrypted);
        String redecrypted = maxsec.redecrypt(reencrypted, pwPosition);
        System.out.println(redecrypted);

        int[] enP = maxsec.encryptedPosition(redecrypted);
        String ddecrypted = maxsec.decrypted(enP, pwPosition);
        System.out.println(ddecrypted);
    }
    String longPassword(String password, String toBeEncrypted){
        StringBuilder longPassword = new StringBuilder(password);
        int i = 0;
        while(longPassword.length()<=toBeEncrypted.length()){
            longPassword.append(password.charAt(i));
            i++;
            if (i>=password.length()) {
                i=0;
            }
        }
        return longPassword.toString();
    }
    int[] passwordPosition(String longPassword){        
        int[] passwordPosition = new int[longPassword.length()];
        for (int i = 0; i < passwordPosition.length; i++) {
            for (int j = 0; j < elements.length; j++) {
                if(longPassword.charAt(i)==(elements[j])){
                    passwordPosition[i]=j;
                    //System.out.println("pw" + i + " " +j);
                }
            }
        }
        return passwordPosition;
    } 
    int[] tBEPosition(String toBeEncrypted){
        int[] tBEPosition = new int[toBeEncrypted.length()];
        for (int i = 0; i < tBEPosition.length; i++) {
            for (int j = 0; j < elements.length; j++) {
                if (toBeEncrypted.charAt(i)==elements[j]) {
                    tBEPosition[i]=j;

                }
            }
        }
        return tBEPosition;
    }
    int[] sums(int[] pwPosition, int[] tBEPosition){
        int[] sums = new int[tBEPosition.length];
        for (int i = 0; i < tBEPosition.length; i++) {
           sums[i]=pwPosition[i]+tBEPosition[i];
        }
    return sums;
    }
    String encrypted(int[] sums){
        StringBuilder strb = new StringBuilder("");
        for (int i = 0; i < sums.length; i++) {
            if (sums[i]>=elements.length) {
                strb.append(elements[sums[i]-elements.length]);
            }
            else{
            strb.append(elements[sums[i]]);
            }
        }
        return strb.toString();
    }
    int[] encryptedPosition(String encrypted){
        int[] encryptedPosition = new int[encrypted.length()];
        for (int i = 0; i < encryptedPosition.length; i++) {
            encryptedPosition[i]= new String(elements).indexOf(encrypted.charAt(i));
        }
        for (int w : encryptedPosition) {
            System.out.println(w);
        }
        return encryptedPosition;
    }
    String decrypted(int[] encryptedPosition, int[] passwordPosition){
        StringBuilder strb = new StringBuilder("");
        for (int i = 0; i < encryptedPosition.length; i++) {
            if ((encryptedPosition[i]-passwordPosition[i])<=elements.length && (encryptedPosition[i]-passwordPosition[i])>=0) {
                strb.append(elements[encryptedPosition[i]-passwordPosition[i]]);
                System.out.println("eins " + (encryptedPosition[i]-passwordPosition[i]));
            }
            else if((encryptedPosition[i]-passwordPosition[i])>elements.length){
                strb.append(elements[(encryptedPosition[i]-passwordPosition[i])-elements.length]);
                System.out.println("zwei " + (encryptedPosition[i]-passwordPosition[i]));
            }
            else{
                strb.append(elements[encryptedPosition[i]-passwordPosition[i]+elements.length]);
                System.out.println("drei " + (encryptedPosition[i]-passwordPosition[i]+elements.length));
            }
        }
        //strb.append("hh");
        return strb.toString();
    }

    static String generateRandomString(int length){
        StringBuilder strb = new StringBuilder("");
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int random = secureRandom.nextInt(elements.length);
            strb.append(elements[random]);
        }
        return strb.toString();
    }
    String[] generateArrayOfRandomStrings(int length, int[] passwordPosition){
        String[] randomStringArray = new String[length];
        for (int i = 0; i < randomStringArray.length; i++) {
            randomStringArray[i] = generateRandomString(passwordPosition[i]);
        }
        
        for (int i = 0; i < randomStringArray.length; i++) {
            System.out.println(randomStringArray[i].length() + "  " + passwordPosition[i]);
        }
        return randomStringArray;
    }
    String reencrypt(String encrypted, String[] randomStringArray){
        StringBuilder strb = new StringBuilder("");
        int counter = 0;
        for (int i = 0; i < encrypted.length(); i++) {
            strb.append(randomStringArray[i]);
            strb.append(encrypted.charAt(i));
        }
        return strb.toString();
    }

    String redecrypt(String reencrypted, int[] passwordPosition){
        StringBuilder strb = new StringBuilder(reencrypted);
        int counter = 0;
        for (int i = 0; i < passwordPosition.length;) {
            strb.delete(counter, counter+passwordPosition[i]);
            counter++;
            i++;
        }
        return strb.toString();
    }
}