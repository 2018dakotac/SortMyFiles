package SortMyFiles.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Button button_back;
    @FXML
    private TextField tagName;
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
        String tag = tagName.getText();
        //Search in db for tag


        //Use a for loop to iterate through array and add files 1by1
        table_File file = new table_File("","Path");
        tableResult.getItems().add(file);
    }
}
