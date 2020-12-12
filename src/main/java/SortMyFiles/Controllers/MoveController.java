package SortMyFiles.Controllers;

import SortMyFiles.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.ResourceBundle;

public class MoveController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Button button_back;
    @FXML
    private Label label_destination;
    @FXML
    private Label Error_label;

    public boolean dirchosen = false;
    //Table
    @FXML
    private TableView<table_File> tableView;
    @FXML
    private TableColumn<table_File, String> colName;
    @FXML
    private  TableColumn<table_File, String> colDirectory;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Table
        colName.setCellValueFactory(new PropertyValueFactory<table_File, String>("Name"));
        colDirectory.setCellValueFactory(new PropertyValueFactory<table_File, String>("Directory"));
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    @FXML
    private void Filechooser_file(ActionEvent event) throws IOException{
        try {
            FileChooser fc = new FileChooser();
            //fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files","*pdf"));
            List<File> f = fc.showOpenMultipleDialog(null);
            if (f != null) {
                for (File file : f) {
                    table_File newFile = new table_File(file.getName(), file.getAbsolutePath());
                    tableView.getItems().add(newFile);
                }
            }
        }
        catch (Exception e){
            //stub
        }
    }
    @FXML
    private void Filechooser_dir(ActionEvent event) throws IOException{
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File dir = directoryChooser.showDialog(null);
            if( dir != null) {
                table_File newFile = new table_File(dir.getName(), dir.getAbsolutePath());
                tableView.getItems().add(newFile);
            }
        }
        catch (Exception e){
            //stub
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) throws IOException{
        try {
            List<table_File> allfiles, selectedfiles;
            allfiles = tableView.getItems();
            selectedfiles = tableView.getSelectionModel().getSelectedItems();
            for (table_File file : selectedfiles) {
                allfiles.remove(file);
            }
        }
        catch(Exception e){
            //stub
        }
    }
    @FXML
    private void load_back(ActionEvent event) throws IOException {
        Effect effect = new Effect();
        effect.load_back(event, button_back, anchorRoot);
    }

    @FXML
    private void destination(ActionEvent event) throws  IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(null);
        if(file != null){
            //label_destination.setText("Selected Destination : " + file.getAbsolutePath());
            label_destination.setText(file.getAbsolutePath());
            dirchosen = true;
        }


    }

    @FXML
    private void Move(ActionEvent event) {
        //Ensure dir is selected
        if(dirchosen == false){
            Error_label.setText("Error: Choose a destination directory");
        }
        else{
            try {
                Error_label.setText("");
                //move
                List<table_File> allfiles;
                allfiles = tableView.getItems();
                //should make a wrapper class that handles moving a directory or file to make this cleaner
                if (miscFunc.isDirectoryPath(allfiles.get(0).getDirectory())) {
                    DirectoryFunctions mvDir = new DirectoryFunctions();
                    for (table_File file : allfiles) {
                        //mvDir.moveDirectory(file.getDirectory(), rando.combine(label_destination.getText(), file.getName()));
                        //mvDir.moveDirectory(file.getDirectory(),label_destination.getText());
                        mvDir.moveDirectoryContents(file.getDirectory(), label_destination.getText());
                    }
                } else {
                    MoveFile mv = new MoveFile();
                    for (table_File file : allfiles) {
                        //mv.moveFile(rando.combine(file.getDirectory(),file.getName()),label_destination.getText());
                        mv.moveFile(file.getDirectory(), miscFunc.combine(label_destination.getText(), file.getName()));
                    }
                }
                //clear table contents
                tableView.getItems().clear();
                //Clear Destination
                label_destination.setText("");
                //display a message files Moved
                Error_label.setText("Files have been moved :)");
            }
            catch (FileAlreadyExistsException e){
                Error_label.setText("Error: Files with the same names are being moved");
            }
            catch (FileSystemException e){
                Error_label.setText("Error: File is being used");
            }
            catch (IOException e){
                Error_label.setText("Error: " + e.getMessage());
            }
            catch (IndexOutOfBoundsException e){
                Error_label.setText("Error: Ensure files are added to the list");
            }
        }
    }
}
