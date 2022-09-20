package com.example.cssdemo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class TableEditController implements Initializable {

    @FXML
    private CheckBox soldCheck;

    @FXML
    private Button submitButton;

    @FXML
    private TextField brandField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Double> sizeBox;

    @FXML
    private TextField skuField;

    @FXML
    private TextField nameField;

    @FXML
    private Button deleteEntryButton;

    @FXML
    private ListView<String> itemList;

    String currentShoe;
    Connection connection = DBConnect.getNewConnection();
    ObservableList<String> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Get shoe names from database
        try {
            ResultSet rs = connection.createStatement().executeQuery("select name from sneaker"); //get names of sneakers
            while (rs.next()) {
                obList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        itemList.setItems(obList);

        itemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                ResultSet shoeData;
                currentShoe = itemList.getSelectionModel().getSelectedItem();
                try {
                    shoeData = connection.createStatement().executeQuery("select * from sneaker where name = '"+currentShoe+"'");
                    while (shoeData.next()){
                        //Setting fields on selection to current saved data
                        nameField.setText(shoeData.getString("name"));
                        brandField.setText(shoeData.getString("brand"));
                        skuField.setText(shoeData.getString("sku"));
                        priceField.setText(String.valueOf(shoeData.getDouble("price")));
                        sizeBox.setValue(shoeData.getDouble("size"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        for (double i = 4; i < 13; i+= 0.5) {
            sizeBox.getItems().add(i);
        }
        sizeBox.setAccessibleText("Size");
    }

    @FXML
    void submitButton(ActionEvent event) throws SQLException {
        connection = DBConnect.getNewConnection();

        String name = nameField.getText(); String brand = brandField.getText();
        String sku = skuField.getText(); Double price = Double.valueOf(priceField.getText());
        Double size = sizeBox.getValue();

        PreparedStatement myStmt = connection.prepareStatement("update sneaker set name = ?, brand = ?, sku = ?, price = ?, size = ? where name = ?"); // update database
        //set variables in prepared statements
        myStmt.setString(1, name); myStmt.setString(2, brand);
        myStmt.setString(3, sku); myStmt.setDouble(4, price);
        myStmt.setDouble(5, size); myStmt.setString(6, currentShoe);
        myStmt.execute();

        nameField.clear(); brandField.clear(); skuField.clear(); priceField.clear(); sizeBox.setValue(null);

        //If name of shoe changes, refresh list of shoes
        if (!Objects.equals(nameField.getText(), currentShoe)) {
            obList.clear();
            ResultSet rs = connection.createStatement().executeQuery("select name from sneaker");
            while (rs.next()) {
                obList.add(rs.getString("name"));
            }
        }
    }

    @FXML
    void deleteEntry(ActionEvent event) throws SQLException {
        //connection = DBConnect.getNewConnection();

        AtomicBoolean deleteEntry = new AtomicBoolean(false);
        //System.out.println(deleteEntry);

        Stage s = new Stage();
        TilePane t = new TilePane();
        Label text = new Label("Are you sure you want to delete this entry?");
        text.setFont(new Font("System", 16));
        text.setWrapText(true);
        text.setTextAlignment(TextAlignment.CENTER); text.setMaxWidth(250);
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        t.getChildren().add(text);
        t.getChildren().add(yesButton);
        t.getChildren().add(noButton);

        Scene sc = new Scene(t, 250, 150);
        s.setScene(sc);
        s.setTitle("Confirmation");
        s.show();

        yesButton.setOnMouseClicked(e ->{
            s.close();
            try {
                removeEntryFromDatabase();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("deleteEntry = " + deleteEntry);
        });

        noButton.setOnMouseClicked(e -> {
            s.close();
        });

//        Home.refreshTable();
    }

    void removeEntryFromDatabase() throws SQLException {
        currentShoe = nameField.getText();

        connection.createStatement().execute("delete from sneaker where name = '" + currentShoe + "'");
        final int selectedIndex = itemList.getSelectionModel().getSelectedIndex();
        itemList.getItems().remove(selectedIndex);

        nameField.clear(); brandField.clear(); skuField.clear(); priceField.clear();
    }
}
