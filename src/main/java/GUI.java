import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * GUI.java
 *
 * @author Kush Bharakhada
 */

public class GUI extends JFrame {

    // Instance variables
    private JButton openFile;
    private JButton saveFile;
    private JButton encryptButton;
    private JButton decryptButton;
    private JTextArea textArea;
    private JTextArea informationTextArea;

    // Constructor
    public GUI() {
        // Frame attributes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("File Encryption");
        this.setSize(800, 500);
        this.setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Open file button
        openFile = new JButton("Open File");
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(openFile, gbc);

        // Save file button
        saveFile = new JButton("Save File");
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(saveFile, gbc);

        // Encrypt panel
        JPanel encryptPanel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        encryptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(encryptPanel, gbc);
        // Encrypt title
        JLabel encryptHeader = new JLabel("ENCRYPT");
        encryptPanel.add(encryptHeader);

        // Encrypt button
        encryptButton = new JButton("ENCRYPT");
        encryptPanel.add(encryptButton);

        // Decrypt panel
        JPanel decryptPanel = new JPanel();
        gbc.gridx = 1;
        gbc.gridy = 1;
        decryptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(decryptPanel, gbc);
        // Decrypt title
        JLabel decryptHeader = new JLabel("DECRYPT");
        decryptPanel.add(decryptHeader);

        // Text area from file
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.add(textArea, gbc);

        // Scrolling area for file text area
        JScrollPane textScrollArea = new JScrollPane(textArea);
        textScrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(textScrollArea, gbc);

        // Information area for dialog
        informationTextArea = new JTextArea();
        informationTextArea.setLineWrap(true);
        informationTextArea.setWrapStyleWord(true);
        // This is a dialog, not be edited by the user
        informationTextArea.setEditable(true);
        informationTextArea.append("Provide the text file to encrypt or decrypt.");

        // Information area for dialog scrollable
        JScrollPane informationAreaScroll = new JScrollPane(informationTextArea);
        informationAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        informationAreaScroll.setPreferredSize(new Dimension(400, 400));
        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(informationAreaScroll, gbc);


        addActionListeners();
        this.setVisible(true);
    }

    public void addActionListeners() {

        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChoice = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
                fileChoice.setFileFilter(filter);

                // Check open has been clicked
                int explorerButton = fileChoice.showOpenDialog(null);
                Scanner readFromFile = null;

                if (explorerButton == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChoice.getSelectedFile().getAbsolutePath());
                    try {
                        readFromFile = new Scanner(file);
                        if (file.isFile()) {
                            String data = "";
                            while (readFromFile.hasNextLine()) {
                                data += readFromFile.nextLine() + "\n";
                            }
                            textArea.append(data);
                        }
                    }
                    catch(FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    finally {
                        readFromFile.close();
                    }
                }
            }
        });

        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChoice = new JFileChooser();
                // Check save has been clicked
                int explorerButton = fileChoice.showSaveDialog(null);

                if (explorerButton == JFileChooser.APPROVE_OPTION) {
                    File file = new File(fileChoice.getSelectedFile().getAbsolutePath());
                    try {
                        // Write the text area to the file
                        FileWriter writeToFile = new FileWriter(file);
                        writeToFile.write(textArea.getText());
                        writeToFile.close();
                    }
                    catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

}


