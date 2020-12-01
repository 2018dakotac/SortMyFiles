package SortMyFiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch(args);   //Sets program as JavaFx and initates start
        System.out.println("hellow world");
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/App.fxml"));

        stage.setTitle("Title");
        stage.setScene(new Scene(root, 300, 500));


        stage.show();

    }
}
