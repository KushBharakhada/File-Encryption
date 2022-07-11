import java.util.Base64;

/**
 * FormattingAndConversion.java
 *
 * @author Kush Bharakhada
 */

public class FormattingAndConversion {

    private static final String TAG = ":encrypted";

    public static String byteToHex(byte[] byteArray) {
        // Empty string initialisation
        String hexString = "";

        // Iterate through each byte
        for (byte b : byteArray) {
            hexString += String.format("%02X", b);
        }
        return hexString;
    }

    public static String encodeData(byte[] data) {
        // Convert binary/byte data into text format
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decodeData(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static String addEncryptionTag(String data) {
        // Add a tag at the start of the data
        return TAG + data;
    }

    public static String removeEncryptionTag(String data) {
        // Remove the tag from the start of the data
        return data.substring(TAG.length(), data.length());
    }

    public static boolean isDataEncrypted(String data) {
        // Check if tag (data is encrypted) is in the data
        if (data.indexOf(TAG) != -1)
            return true;
        return false;
    }

    public static  void main(String[] args) {
        String hi = "hello";
        System.out.println("------- ORIGINAL -----");
        System.out.println(hi);
        System.out.println("Is data encrypted? " + isDataEncrypted(hi));

        System.out.println("------- ENCODE -----");
        byte[] bytesWord = hi.getBytes();
        String enc = encodeData(bytesWord);
        System.out.println(enc);

        System.out.println("------- ADD TAG -----");
        String tagAdd = addEncryptionTag(enc);
        System.out.println(tagAdd);
        System.out.println("Is data encrypted? " + isDataEncrypted(tagAdd));

        System.out.println("------- REMOVE TAG -----");
        String removeTag = removeEncryptionTag(tagAdd);
        System.out.println(removeTag);

        System.out.println("------- DECODE -----");
        String dec = new String(decodeData(removeTag));
        System.out.println(dec);
        System.out.println("Is data encrypted? " + isDataEncrypted(dec));
    }

}
