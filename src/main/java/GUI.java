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

public class GUI extends JFrame {

    // Instance variables
    private JButton openFile;
    private JButton saveFile;
    private JTextArea textArea;
    private JScrollPane textScrollArea;
    private JTextArea informationTextArea;

    // Constructor
    public GUI() {
        // Frame attributes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("File Encryption");
        this.setSize(800, 500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Open file button
        openFile = new JButton("Open File");
        openFile.setBounds(100, 10, 100, 25);
        this.add(openFile);

        // Save file button
        saveFile = new JButton("Save File");
        saveFile.setBounds(225, 10, 100, 25);
        this.add(saveFile);

        // Encrypt panel
        JPanel encryptPanel = new JPanel();
        encryptPanel.setBounds(20, 50, 400, 100);
        encryptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(encryptPanel);
        // Encrypt title
        JLabel encryptHeader = new JLabel("ENCRYPT");
        encryptPanel.add(encryptHeader);

        // Decrypt panel
        JPanel decryptPanel = new JPanel();
        decryptPanel.setBounds(20, 180, 400, 100);
        decryptPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(decryptPanel);
        // Decrypt title
        JLabel decryptHeader = new JLabel("DECRYPT");
        decryptPanel.add(decryptHeader);

        // Text area from file
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.add(textArea);

        // Scrolling area
        textScrollArea = new JScrollPane(textArea);
        textScrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        textScrollArea.setBounds(450, 10, 300, 410);
        this.add(textScrollArea);

        // Information area for dialog
        informationTextArea = new JTextArea();
        informationTextArea.setLineWrap(true);
        informationTextArea.setWrapStyleWord(true);
        // This is a dialog, not be edited by the user
        informationTextArea.setEditable(false);
        informationTextArea.setBounds(20, 300, 400, 100);
        informationTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        informationTextArea.append("Provide the text file to encrypt or decrypt.");
        this.add(informationTextArea);

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

















    /*
    @Override
    public void actionPerformed(ActionEvent e) {

        // Action for open file
        if (e.getSource() == openFile) {
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


        // Action for save file
        if (e.getSource() == saveFile) {

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

    }
    */


}


