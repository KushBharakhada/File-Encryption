import javax.swing.*;

/**
 * GUIFrame.java
 *
 * Sets up the frame window for the GUI.
 *
 * @author Kush Bharakhada
 */

public class GUIFrame extends JFrame {

    public GUIFrame() {
        // Setting up the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("File Encryption");
        GUIPanel panel = new GUIPanel();
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

}