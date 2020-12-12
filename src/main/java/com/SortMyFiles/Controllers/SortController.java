package com.SortMyFiles.Controllers;


import com.SortMyFiles.SortFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class SortController implements Initializable {

    ObservableList<String> sortbyList = FXCollections.observableArrayList("Extension", "Tag");
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private ComboBox<String> Sortby;
    @FXML
    private Button button_back;
    @FXML
    private Label label_destination;
    @FXML
    private Label Error_label;
    public boolean dirchosen = false;



    @FXML
    private TableView<table_File> tableView;
    @FXML
    private TableColumn<table_File, String> colName;
    @FXML
    private  TableColumn<table_File, String> colDirectory;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        Sortby.setItems(sortbyList);
        Sortby.setValue("Extension");
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
            for (File file : f) {
                if( f != null) {
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
    private void deleteButton(ActionEvent event) {
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
    private void Sort(ActionEvent event)  {
        //Ensure dir is selected
        if(dirchosen == false){
            Error_label.setText("Error: Choose a destination directory");
        }
        else{
            try {
                Error_label.setText("");
                //Sort
                String result = Sortby.getValue();
                SortFile sort = new SortFile();
                List<table_File> allfiles;
                allfiles = tableView.getItems();
                if(allfiles.isEmpty()){
                    throw new IndexOutOfBoundsException();
                }
                if (result == "Extension") {
                    for (table_File file : allfiles) {
                        //need to parse file extension
                        sort.extensionSort(file.getDirectory(), label_destination.getText());
                    }
                }  else if (result == "Tag") {
                    for (table_File file : allfiles) {
                        //need to parse file extension
                        sort.tagSort(file.getDirectory(), label_destination.getText());
                    }
                } else {
                    //stub
                }
                //clear table contents
                tableView.getItems().clear();
                //display a message files sorted
                Error_label.setText("Files have been sorted :)");
            }catch(UncheckedIOException e){
                Error_label.setText("Error: " +e.getCause());
            }
            catch (FileAlreadyExistsException e){
                Error_label.setText("Error: Files with the same names are being moved into a folder");
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
