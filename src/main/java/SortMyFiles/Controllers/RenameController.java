package SortMyFiles.Controllers;

import SortMyFiles.MoveFile;
import SortMyFiles.miscFunc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
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
    @FXML
    private Label Error_label;
    @Override
    public void initialize(URL url, ResourceBundle rb){

    }
    private String filePath ="";
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
                filePath = f.getAbsolutePath();
            }
        }
        catch (Exception e){
            //stub
        }
    }
    @FXML
    private void Rename(ActionEvent event) throws IOException{
        try {
            String name = newName.getText();
            if(label_chosen.getText() == ""){
                throw new IndexOutOfBoundsException();
            }
            if (name == "") {
                throw new Exception();
            }
            //ensure name is valid
            if (miscFunc.isFilenameValid(name)) {
                //rename
                if (miscFunc.isDirectoryPath(label_chosen.getText())) {
                    //not enough time to deal with renaming non empty directory
                } else {
                    MoveFile mv = new MoveFile();
                    mv.renameFile(filePath, name);
                }
            }
            //clear
            label_chosen.setText("");
            newName.setText("");
            filePath = "";
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
        catch (Exception e){
            Error_label.setText("No new name entered");
        }
    }
}
