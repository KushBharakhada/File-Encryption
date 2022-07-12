import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private JButton generateKeyButton;
    private JTextArea textArea;
    private JTextArea informationTextArea;
    private JTextField generateKeyTextArea;
    private JTextField loadKeyTextArea;
    private EncryptionDecryption encryptionDecryption;

    // Constructor
    public GUI() {
        // Frame attributes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("File Encryption");
        this.setLayout(new GridLayout(1, 2));
        encryptionDecryption = new EncryptionDecryption();

        // ********** LEFT MAIN PANEL ***********

        JPanel leftMainPanel = new JPanel();
        leftMainPanel.setLayout(new BoxLayout(leftMainPanel, BoxLayout.Y_AXIS));

        // ********** OPEN AND SAVE PANEL **********

        JPanel openAndSavePanel = new JPanel();
        openAndSavePanel.setLayout(new FlowLayout());
        openAndSavePanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Open file button
        openFile = new JButton("Open File");
        openFile.setPreferredSize(new Dimension(100, 25));
        openAndSavePanel.add(openFile);

        // Save file button
        saveFile = new JButton("Save File");
        saveFile.setPreferredSize(new Dimension(100, 25));
        openAndSavePanel.add(saveFile);

        leftMainPanel.add(openAndSavePanel);

        // ********** OPEN AND SAVE PANEL **********

        JPanel encryptAndDecryptPanel = new JPanel();
        encryptAndDecryptPanel.setLayout(new FlowLayout());

        // Encrypt button
        encryptButton = new JButton("ENCRYPT");
        encryptButton.setPreferredSize(new Dimension(100, 25));
        encryptAndDecryptPanel.add(encryptButton);

        // Decrypt button
        decryptButton = new JButton("DECRYPT");
        decryptButton.setPreferredSize(new Dimension(100, 25));
        encryptAndDecryptPanel.add(decryptButton);

        leftMainPanel.add(encryptAndDecryptPanel);

        // ********** KEY PANEL **********

        JPanel keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(2, 2));
        keyPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Generate key button
        generateKeyButton = new JButton("Generate Key");
        keyPanel.add(generateKeyButton);

        // Key generated text area
        generateKeyTextArea = new JTextField();
        generateKeyTextArea.setEditable(false);

        // Scrolling area for generated key
        JScrollPane generatedKeyTextScrollArea = new JScrollPane(generateKeyTextArea);
        generatedKeyTextScrollArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        keyPanel.add(generatedKeyTextScrollArea);

        // Load key label
        JLabel loadKeyLabel = new JLabel("Load Key: ");
        // Center align the label
        loadKeyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        keyPanel.add(loadKeyLabel);

        // Load key text area
        loadKeyTextArea = new JTextField();
        //keyPanel.add(loadKeyTextArea);

        // Scrolling area for generated key
        JScrollPane loadKeyTextScrollArea = new JScrollPane(loadKeyTextArea);
        loadKeyTextScrollArea.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        keyPanel.add(loadKeyTextScrollArea);

        leftMainPanel.add(keyPanel);

        // ********** INFORMATION PANEL **********
        JPanel informationAreaPanel = new JPanel();
        informationAreaPanel.setLayout(new BoxLayout(informationAreaPanel, BoxLayout.Y_AXIS));
        informationAreaPanel.setPreferredSize(new Dimension(400, 400));
        informationAreaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Information Label
        JLabel informationLabel = new JLabel("INFORMATION");
        informationLabel.setAlignmentX(CENTER_ALIGNMENT);
        informationAreaPanel.add(informationLabel);

        // Information area for dialog
        informationTextArea = new JTextArea();
        informationTextArea.setLineWrap(true);
        informationTextArea.setWrapStyleWord(true);
        // This is a dialog, not be edited by the user
        informationTextArea.setEditable(false);
        informationTextArea.append("Provide the text file to encrypt or decrypt.");

        // Information area for dialog scrollable
        JScrollPane informationAreaScroll = new JScrollPane(informationTextArea);
        informationAreaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        informationAreaPanel.add(informationAreaScroll);

        leftMainPanel.add(informationAreaPanel);

        this.add(leftMainPanel);

        // *********** RIGHT MAIN PANEL ***********

        JPanel rightMainPanel = new JPanel();
        rightMainPanel.setLayout(new BoxLayout(rightMainPanel, BoxLayout.Y_AXIS));
        rightMainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightMainPanel.setBackground(Color.BLACK);

        // Label for text area from file
        JLabel textAreaLabel = new JLabel("FILE DATA");
        textAreaLabel.setAlignmentX(CENTER_ALIGNMENT);
        textAreaLabel.setForeground(Color.WHITE);
        rightMainPanel.add(textAreaLabel);

        // Text area from file
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Scrolling area for file text area
        JScrollPane textScrollArea = new JScrollPane(textArea);
        textScrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        rightMainPanel.add(textScrollArea);

        this.add(rightMainPanel);

        addActionListeners();
        this.pack();
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

        //TODO
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input key from the user
                String userInputKey = loadKeyTextArea.getText();
                // Encrypt the text and convert the string key to a key object
                String encryptedText = encryptionDecryption.encrypt(textArea.getText(),
                        EncryptionDecryption.stringToKey(userInputKey));
                textArea.setText(encryptedText);
            }
        });

        //TODO
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Input key from the user
                String userInputKey = loadKeyTextArea.getText();
                // Decrypt the text and convert the string key to a key object
                String decryptedText = encryptionDecryption.decrypt(textArea.getText(),
                        EncryptionDecryption.stringToKey(userInputKey));
                textArea.setText(decryptedText);
            }
        });

        //TODO
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a key and display in text box
                String key = FormattingAndConversion.byteToHex(EncryptionDecryption.createKey().getEncoded());
                generateKeyTextArea.setText(key);
            }
        });

    }

}


