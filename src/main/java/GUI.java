import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame implements ActionListener {

    // Instance variables
    private JButton openFile;
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
        openFile.addActionListener(this);
        this.add(openFile);

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


        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Action for open file
        if (e.getSource() == openFile) {
            JFileChooser fileChoose = new JFileChooser();
            // Select file to open
            int explorerButton = fileChoose.showOpenDialog(null);

            if (explorerButton == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChoose.getSelectedFile().getAbsolutePath());
            }

        }

    }

}


