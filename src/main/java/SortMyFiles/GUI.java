package SortMyFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class GUI {
    public JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField fileLocationPathTextField;
    private JPanel mainMenuPanel;
    private JPanel filesPanel;
    private JTextField newFilePathLocationTextField;
    private JButton moveFileButton;
    private JButton swapFilePathsButton;
    private JTextField fileAttributesPath;
    private JTextArea resultTextArea;
    private JButton listAttributesButton;
    private JTextField createDirTextField;
    private JTextField dSAMPLESORTINGDIRECTORYTextField;
    private JButton deleteDirectoryButton;
    private JTextArea DirectoryResultTextArea;
    private JButton createDirectoryButton;

    public GUI() {
        moveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileLocation = fileLocationPathTextField.getText();
                String fileNewLocation = newFilePathLocationTextField.getText();
                FileMove test = new FileMove();
                test.moveFile(fileLocation,fileNewLocation);
            }
        });
        swapFilePathsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileLocation = fileLocationPathTextField.getText();
                String fileNewLocation = newFilePathLocationTextField.getText();
                fileLocationPathTextField.setText(fileNewLocation);
                newFilePathLocationTextField.setText(fileLocation);

            }
        });
        listAttributesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = fileAttributesPath.getText();
                FileAttributes test = new FileAttributes();
                String result = test.listFileAttributes(filePath);
                resultTextArea.setText(result);
            }
        });
        createDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DirectoryFunctions test = new DirectoryFunctions();
                test.createDirectory(createDirTextField.getText());
            }
        });
    }
}
