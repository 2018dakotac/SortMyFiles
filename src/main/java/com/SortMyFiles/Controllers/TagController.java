package com.SortMyFiles.Controllers;

import com.SortMyFiles.FileDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TagController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Button button_back;
    @FXML
    private TextField add_Tagtext;
    @FXML
    private Button button_addtag;
    @FXML
    private Label label_tag;
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
    private void addTag(ActionEvent event) throws IOException{
        String newTag = add_Tagtext.getText();
        try {
            if (newTag == "" || newTag == " ") throw new Exception();
            else {
                label_tag.setText(newTag);
                add_Tagtext.setText("");
            }
        }
        catch (Exception e){
            Error_label.setText("Missing Tag!");
        }
    }

    @FXML
    private void Tag(ActionEvent event) throws IOException{
        try {
            Error_label.setText("");
            String Tag = label_tag.getText();
            if(Tag == ""){
                throw new Exception();
            }
            //Add files with tag to db
            //check if tag already exists or need to add to db
            //repeat tags will be allowed :(
            List<table_File> allfiles;
            allfiles = tableView.getItems();
            if(allfiles.isEmpty()){
                throw new IndexOutOfBoundsException();
            }
            FileDatabase db = new FileDatabase();
            for (table_File file : allfiles) {
                db.insertFile(file.getDirectory());
                db.overwriteTag(file.getDirectory(), Tag);
            }
            //clear table contents
            tableView.getItems().clear();
            label_tag.setText("");
            Error_label.setText("File has been tagged");
        }catch(UncheckedIOException e){
            Error_label.setText("Error: " +e.getCause());
        }
        catch (IndexOutOfBoundsException e) {
            Error_label.setText("Error: Ensure files are added to the list");
        }
        catch (Exception e){
            Error_label.setText("Error: Missing Tag!");
        }
    }
}
