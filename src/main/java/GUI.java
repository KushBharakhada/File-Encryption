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

    // Constructor
    public GUI() {
        // Frame attributes
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("File Encryption");
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        // Open file button
        openFile = new JButton("Open File");
        this.add(openFile);

        // Save file button
        saveFile = new JButton("Save File");
        this.add(saveFile);

        // Text area
        textArea = new JTextArea();
        //textArea.setPreferredSize(new Dimension(200, 200));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.add(textArea);

        // Scrolling area
        textScrollArea = new JScrollPane(textArea);
        textScrollArea.setPreferredSize(new Dimension(300, 300));
        textScrollArea.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(textScrollArea);

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


