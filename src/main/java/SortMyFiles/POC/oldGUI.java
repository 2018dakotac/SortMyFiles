package SortMyFiles.POC;

import SortMyFiles.POC.DirectoryFunctions;
import SortMyFiles.POC.FileAttributes;
import SortMyFiles.POC.FileMove;
import SortMyFiles.POC.SearchFunctions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class oldGUI {
    public JPanel mainPanel;
    private JTabbedPane searchPanel;
    private JTextField fileLocationPathTextField;
    private JPanel mainMenuPanel;
    private JPanel filesAttributePanel;
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
    private JPanel directoryPanel;
    private JTextField showFilesDirTextField;
    private JTextArea showFilesTextArea;
    private JButton showDirContentButton;
    private JButton showSubFoldersButton;
    private JPanel showFilesPanel;
    private JTextField searchExtensionTextFile;
    private JTextField searchDirectoryPathTextField;
    private JTextArea searchTextArea;
    private JButton searchButton;

    public oldGUI() {
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
        showDirContentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilesTextArea.setText("");
                SearchFunctions search = new SearchFunctions();
                String[] pathnames = search.listAllFiles(showFilesDirTextField.getText());
                for (int i = 0; i< pathnames.length;i++){
                    showFilesTextArea.append(pathnames[i]);
                    showFilesTextArea.append("\n");
                }



            }
        });
        showSubFoldersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFilesTextArea.setText("");
                SearchFunctions search = new SearchFunctions();
                String[] foldernames = search.listAllSubDirectories(showFilesDirTextField.getText());
                for (int i = 0; i< foldernames.length;i++){
                    showFilesTextArea.append(foldernames[i]);
                    showFilesTextArea.append("\n");
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTextArea.setText("");
                SearchFunctions search = new SearchFunctions();
                String[] matchingFiles = search.listCertainExtension(searchDirectoryPathTextField.getText(),searchExtensionTextFile.getText());
                for (int i = 0; i< matchingFiles.length;i++){
                    searchTextArea.append(matchingFiles[i]);
                    searchTextArea.append("\n");
                }
            }
        });
    }
}
