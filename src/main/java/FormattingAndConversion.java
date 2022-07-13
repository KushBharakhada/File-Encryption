import java.util.Base64;

/**
 * FormattingAndConversion.java
 *
 * Provides conversions required for the encryption and decryption process.
 * Formats/modifies the encrypted data to see whether the data is already
 * encrypted or decrypted (to avoid allowing multiple encryption/decryption on a single file).
 *
 * @author Kush Bharakhada
 */

public class FormattingAndConversion {

    // This tag signifies if data is already encrypted/decrypted
    // Appended to the start of the encrypted data and removed before decryption
    private static final String TAG = ":encrypted";

    public static String byteToHex(byte[] byteArray) {
        // Empty string at initialisation
        String hexString = "";

        // Iterate through each byte
        for (byte b : byteArray) {
            hexString += String.format("%02X", b);
        }
        return hexString;
    }

    public static String encodeData(byte[] data) {
        // Convert binary/byte data into text format
        return Base64.getMimeEncoder().encodeToString(data);
    }

    public static byte[] decodeData(String data) {
        return Base64.getMimeDecoder().decode(data);
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

}
