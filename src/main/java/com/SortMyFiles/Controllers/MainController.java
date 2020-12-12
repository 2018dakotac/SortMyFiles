package com.SortMyFiles.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button button_move;
    @FXML
    private Button button_sort;
    @FXML
    private Button button_delete;
    @FXML
    private Button button_rename;
    @FXML
    private Button button_tag;
    @FXML
    private Button button_search;

    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    @FXML
    private void loadMove(ActionEvent event) throws IOException {
        load(event, "/Move.fxml", button_move);
    }
    @FXML
    private void loadSort(ActionEvent event) throws IOException {
        load(event, "/Sort.fxml", button_sort);
    }
    @FXML
    private void loadDelete(ActionEvent event) throws IOException {
        load(event, "/Delete.fxml", button_delete);
    }
    @FXML
    private void loadRename(ActionEvent event) throws IOException {
        load(event, "/Rename.fxml", button_rename);
    }
    @FXML
    private void loadTag(ActionEvent event) throws IOException {
        load(event, "/Tag.fxml", button_tag);
    }
    @FXML
    private void loadSearch(ActionEvent event) throws IOException {
        load(event, "/Search.fxml", button_search);
    }
    @FXML
    private void load(ActionEvent event, String filename, Button btn) throws IOException {
        Effect effect = new Effect();
        effect.load(event, filename, btn, anchorRoot, parentContainer);
    }

}
