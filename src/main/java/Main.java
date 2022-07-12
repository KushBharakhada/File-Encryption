/**
 * Main.java
 *
 * @author Kush Bharakhada
 */

public class Main {

    public static void main(String[] args) {

        new GUI();
        GUI.appendInformationMessage("Welcome! \n" +
                "Open a .txt file. Generate a key OR load a pre-existing key. " +
                "Press ENCRYPT to encrypt the data or DECRYPT to decrypt the data. " +
                "A message already encrypted or decrypted cannot again be encrypted or decrypted. " +
                "Save the file to the desired location.");

    }

}
