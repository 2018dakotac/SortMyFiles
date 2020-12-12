package com.SortMyFiles.Controllers;

import com.SortMyFiles.FileDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Button button_back;
    @FXML
    private TextField tagName;
    @FXML
    private Label Error_label;

    //Table
    @FXML
    private TableView<table_File> tableResult;
    @FXML
    private TableColumn<table_File, String> colPath;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        colPath.setCellValueFactory(new PropertyValueFactory<table_File, String>("Directory"));
    }

    @FXML
    private void load_back(ActionEvent event) throws IOException {
        Effect effect = new Effect();
        effect.load_back(event, button_back, anchorRoot);
    }
    @FXML
    private void Search(ActionEvent event) throws IOException {
        try {
            tableResult.getItems().clear();
            Error_label.setText("");
            String tag = tagName.getText();
            if (tag == "") {
                throw new Exception();
            }
            //Search in db for tag
            FileDatabase db = new FileDatabase();
            ArrayList<String> temp = db.findTag(tag);

            if(temp.isEmpty()){
                Error_label.setText("No tags found in database");
            }
            else {
                //Use a for loop to iterate through array and add files 1by1
                for (String s : temp) {
                    table_File file = new table_File("", s);
                    tableResult.getItems().add(file);
                }
            }

        }
        catch(UncheckedIOException e){
            Error_label.setText("Error: " +e.getCause());
        }
        catch (IndexOutOfBoundsException e){
            Error_label.setText("Error: Ensure files are added to the list");
        }
        catch (Exception e){
            Error_label.setText("Error: Enter a tag");
        }
    }
}
