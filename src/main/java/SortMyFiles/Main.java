package SortMyFiles;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);   //Sets program as JavaFx and initates start
        System.out.println("hellow world");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Title");
        stage.setWidth(300);
        stage.setHeight(300);


        stage.show();

    }
}
