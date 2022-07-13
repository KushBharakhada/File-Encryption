import java.util.Arrays;
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

    public static String bytesToHex(byte[] byteArray) {
        // Empty string at initialisation
        String hexString = "";

        // Iterate through each byte
        for (byte b : byteArray) {
            hexString += String.format("%02X", b);
        }
        return hexString;
    }

    public static byte[] hexToBytes(String hexadecimals) {
        // Pairs of hex create a byte so array length is halved
        byte[] bytesArray = new byte[hexadecimals.length() / 2];

        for (int i = 0; i < bytesArray.length; i++) {
            int index = i * 2;
            int value = Integer.parseInt(hexadecimals.substring(index, index + 2), 16);
            bytesArray[i] = (byte)value;
        }
        return bytesArray;
    }

    public static String encodeData(byte[] message) {
        // Convert binary/byte data into text format
        return Base64.getMimeEncoder().encodeToString(message);
    }

    public static byte[] decodeData(String message) {
        return Base64.getMimeDecoder().decode(message);
    }

    public static String addEncryptionTag(String data) {
        // Add a tag at the end of the data
        return data + TAG;
    }

    public static String removeEncryptionTag(String data) {
        // Remove the tag from the start of the data
        return data.substring(0, data.length() - TAG.length());
    }

    public static boolean isDataEncrypted(String data) {
        // Check if tag (data is encrypted) is in the data
        if (data.indexOf(TAG) != -1)
            return true;
        return false;
    }

    public static byte[] concatBytes(byte[] arrayA, byte[] arrayB) {
        byte[] bothArrays = new byte[arrayA.length + arrayB.length];
        System.arraycopy(arrayA, 0, bothArrays, 0, arrayA.length);
        System.arraycopy(arrayB, 0, bothArrays, arrayA.length, arrayB.length);
        return bothArrays;
    }

    public static byte[] getIVBytes(byte[] message) {
        // First 16 bytes is the IV
        byte[] ivBytes = Arrays.copyOfRange(message, 0, 16);
        return ivBytes;
    }

    public static byte[] getDataBytes(byte[] message) {
        // After the first 16 bytes starts the data
        byte[] data = Arrays.copyOfRange(message, 16, message.length);
        return data;
    }

}
