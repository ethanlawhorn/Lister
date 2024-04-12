import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.*;

public class RecursiveLister extends JFrame {
    private JTextArea textArea;

    public RecursiveLister() {
        super("Recursive File Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JButton startButton = new JButton("Choose Directory");
        JButton quitButton = new JButton("Quit");
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);

        startButton.addActionListener(this::displayFileChooser);
        quitButton.addActionListener(e -> System.exit(0));

        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(quitButton);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void displayFileChooser(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File directory = chooser.getSelectedFile();
            listFiles(directory);
        }
    }

    private void listFiles(File dir) {
        try {
            Files.walk(dir.toPath())
                    .forEach(path -> textArea.append(path + "\n"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading directory", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecursiveLister::new);
    }
}