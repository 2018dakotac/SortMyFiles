package SortMyFiles.Controllers;

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
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SortController implements Initializable {

    ObservableList<String> sortbyList = FXCollections.observableArrayList("Extension", "Name", "Size", "Tag");
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private ComboBox<String> Sortby;
    @FXML
    private Button button_back;
    @FXML
    private Label label_destination;
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
            label_destination.setText("Selected Destination : " + file.getAbsolutePath());
            dirchosen = true;
        }
    }

    @FXML
    private void Sort(ActionEvent event) throws IOException{
        //Ensure dir is selected
        if(dirchosen == false){
            //Stub or throw error
        }
        else{
            //Sort
            String result = Sortby.getValue();
            if (result == "Extension") {

            }
            else if( result == "Name"){

            }
            else if( result == "Size"){

            }
            else if( result == "Tag"){

            }
            else{
                //stub
            }
            //clear table contents
            tableView.getItems().clear();
            //display a message files sorted

        }
    }
}
