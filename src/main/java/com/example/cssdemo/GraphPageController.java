package com.example.cssdemo;

import com.mysql.cj.xdevapi.DbDoc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GraphPageController implements Initializable {

    @FXML
    private Button exportButton;

    @FXML
    private Button graphButton;

    @FXML
    private Button importButton;

    @FXML
    private PieChart shoeChart;

    @FXML
    private Button statsButton;

    @FXML
    private Button tableButton;

    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            connection = DBConnect.getConnection();
            ResultSet brand = connection.createStatement().executeQuery("select brand from sneaker"); //get names of sneakers
            while (brand.next()) {
                String shoeBrand = brand .getString("brand");
                ResultSet quantity = connection.createStatement().executeQuery("select count(idSneaker) from sneaker where brand = '"+shoeBrand+"'");
                if (quantity.next()) {
                    PieChart.Data newData = new PieChart.Data(shoeBrand, quantity.getInt(1));
                    shoeChart.getData().add(newData);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
//                new PieChart.Data("Iphone 5S", 13),
//                new PieChart.Data("Samsung Grand", 25),
//                new PieChart.Data("MOTO G", 10),
//                new PieChart.Data("Nokia Lumia", 22));
//
//        shoeChart.setData(pieChartData);
        shoeChart.setStartAngle(180);
        //shoeChart.setLegendSide(Side.LEFT);
        //shoeChart.titleProperty();

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : shoeChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%");
                        }
                    });
        }
    }

    @FXML
    void closeWindow(MouseEvent event) {

    }

    @FXML
    void minimizeWindow(MouseEvent event) {

    }

    @FXML
    void setStatsButton(ActionEvent event) throws IOException {
        HelloApplication h = new HelloApplication();
        statsButton.setOnMouseClicked(e -> {
            try {
                h.changeScene("statsPage.fxml", statsButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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


}
