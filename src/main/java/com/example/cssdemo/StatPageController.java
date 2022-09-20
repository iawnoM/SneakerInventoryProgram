package com.example.cssdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatPageController implements Initializable {

    @FXML
    private Text gridText4;

    @FXML
    private Text gridText3;

    @FXML
    private Button exportButton;

    @FXML
    private Text gridText6;

    @FXML
    private Button tableButton;

    @FXML
    private Text gridText5;

    @FXML
    private Button graphButton;

    @FXML
    private Button statsButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button importButton;

    @FXML
    private Text gridText2;

    @FXML
    private Text gridText1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void setTableButton(ActionEvent event) {
        HelloApplication h = new HelloApplication();
        tableButton.setOnMouseClicked(e -> {
            try {
                h.changeScene("homePage.fxml", tableButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    void setGraphButton(ActionEvent event) {
        HelloApplication h = new HelloApplication();
        graphButton.setOnMouseClicked(e -> {
            try {
                h.changeScene("graphPage.fxml", graphButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}

