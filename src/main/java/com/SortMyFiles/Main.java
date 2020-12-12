package com.SortMyFiles;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch(args);   //Sets program as JavaFx and initates start
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        stage.setTitle("Sort My Files");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
