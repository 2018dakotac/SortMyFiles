package SortMyFiles.Controllers;

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

public class MoveController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Button button_back;
    @FXML
    private Label label_destination;

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
    private void Filechooser(ActionEvent event) throws IOException{
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
        List<table_File> allfiles, selectedfiles;
        allfiles = tableView.getItems();
        selectedfiles = tableView.getSelectionModel().getSelectedItems();
        for(table_File file : selectedfiles){
            allfiles.remove(file);
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
            label_destination.setText("Selected Destination : " + file.getAbsolutePath());
            dirchosen = true;
        }


    }

    @FXML
    private void Move(ActionEvent event) throws IOException{
        //Ensure dir is selected
        if(dirchosen == false){
            //Stub or throw error
        }
        else{
            //move
        }
    }
}
