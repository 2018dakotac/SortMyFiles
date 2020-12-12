package com.SortMyFiles.Controllers;

import com.SortMyFiles.DirectoryFunctions;
import com.SortMyFiles.FileDatabase;
import com.SortMyFiles.MoveFile;
import com.SortMyFiles.miscFunc;
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
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Button button_back;
    @FXML
    private Label Error_label;
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
    private void load_back(ActionEvent event) throws IOException {
        Effect effect = new Effect();
        effect.load_back(event, button_back, anchorRoot);
    }
    @FXML
    private void Filechooser_file(ActionEvent event) throws IOException{
        try {
            FileChooser fc = new FileChooser();
            //fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files","*pdf"));
            List<File> f = fc.showOpenMultipleDialog(null);
            if( f != null) {
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
    private void deletefromlist(ActionEvent event) throws IOException{
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
    private void deleteTag(ActionEvent event) throws IOException{
        List<table_File> allfiles;
        allfiles = tableView.getItems();
        FileDatabase db = new FileDatabase();
        for (table_File file : allfiles) {
            db.deleteFile(file.getDirectory());
        }
        //Delete the Tag on the file
        tableView.getItems().clear();
    }
    @FXML
    private void Delete(ActionEvent event) throws IOException{
        //Ensure table is not empty
        if(tableView.getItems() == null){
            Error_label.setText("Error: Choose a destination directory");
        }
        else{
            try {
                Error_label.setText("");
                //delete from list
                List<table_File> allfiles;
                allfiles = tableView.getItems();
                //should make a wrapper class that handles moving a directory or file to make this cleaner
                if (miscFunc.isDirectoryPath(allfiles.get(0).getDirectory())) {
                    DirectoryFunctions mvDir = new DirectoryFunctions();
                    for (table_File file : allfiles) {
                        mvDir.deleteDirectory(file.getDirectory());
                    }
                } else {
                    MoveFile mv = new MoveFile();
                    for (table_File file : allfiles) {
                        mv.deleteFile(file.getDirectory());
                    }
                }

                //clear table contents
                tableView.getItems().clear();
                //display a message files Deleted
                Error_label.setText("Files have been deleted :)");
            }catch(UncheckedIOException e){
                Error_label.setText("Error: " +e.getCause());
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
