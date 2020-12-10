package SortMyFiles.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RenameController implements Initializable {
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private Label label_chosen;
    @FXML
    private Button button_back;
    @FXML
    private TextField newName;
    @Override
    public void initialize(URL url, ResourceBundle rb){

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
            File f = fc.showOpenDialog(null);
            if(f != null){
                label_chosen.setText(f.getName());
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
                label_chosen.setText(dir.getName());
            }
        }
        catch (Exception e){
            //stub
        }
    }
    @FXML
    private void Rename(ActionEvent event) throws IOException{
        String name = newName.getText();
        System.out.println(name);
        //ensure name is valid

        //rename

        //clear
        label_chosen.setText("");
        newName.setText("");
    }
}
