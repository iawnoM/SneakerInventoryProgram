package com.example.cssdemo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    private Stage stg;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        Parent root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        root.getStylesheets().add(getClass().getResource("fileButton.css").toExternalForm());
        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    //Scene changing method
    public void changeScene(String fxml, Button btn) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));

        Stage window = (Stage) btn.getScene().getWindow();
        window.setScene(new Scene(pane));
//        scene = new Scene(pane);
//        stg.setScene(scene);
//        stg.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}