package com.example.cssdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button button;

    @FXML
    private TextField keyValue;

    @FXML
    private Label invalidKey;

    @FXML
    private TextArea editableKey;

    @FXML
    void login(ActionEvent event) throws IOException, InterruptedException {
        //checkKey();
    }

    int attempts = 4;
//    private void checkKey() throws IOException, InterruptedException {
//        HelloApplication m = new HelloApplication();
//        //String userInput = keyValue.getText().toString();
//        String userInput = editableKey.getText().toString();
//        if (userInput.equals("testing") && attempts-- != 0) {
//            invalidKey.setText("Success!");
//            Thread.sleep(500);
//            m.changeScene("homePage.fxml");
//        } else if (attempts != 0){
//            invalidKey.setText("Invalid Key");
//        } else {
//            invalidKey.setText("Invalid Key");
//            keyValue.setEditable(false);
//        }
//        editableKey.setEditable(true);
//    }
}