package com.SortMyFiles.Controllers;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;

public class Effect {
    public void load(ActionEvent event, String filename, Button btn, AnchorPane anchorRoot, StackPane parentContainer) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(filename));
        Scene scene = btn.getScene();
        //Set Y of second scene to Height of window
        root.translateYProperty().set(scene.getHeight());
        //Add second scene. Now both first and second scene is present
        parentContainer.getChildren().add(root);

        //Create new TimeLine animation
        Timeline timeline = new Timeline();
        //Animate Y property
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }
    public void load_back(ActionEvent event, Button button_back, AnchorPane anchorRoot) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Scene scene = button_back.getScene();
        //Set Y of second scene to Height of window
        root.translateYProperty().set(-scene.getHeight());
        //Add second scene. Now both first and second scene is present
        StackPane parentContainer = (StackPane) scene.getRoot();
        parentContainer.getChildren().add(root);

        //Create new TimeLine animation
        Timeline timeline = new Timeline();
        //Animate Y property
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        timeline.getKeyFrames().add(kf);timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }
}
