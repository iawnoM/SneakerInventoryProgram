package com.example.cssdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Home implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button exportButton;
    @FXML
    private Button importButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<Shoe> shoeTable;
    @FXML
    private TableColumn<Shoe, String> col_size;
    @FXML
    private TableColumn<Shoe, String> col_name;
    @FXML
    private TableColumn<Shoe, String> col_price;
    @FXML
    private TableColumn<Shoe, String> col_sku;
    @FXML
    private TableColumn<Shoe, String> col_brand;
    @FXML
    private TableColumn<Shoe, String> soldCol;
    @FXML
    private TextField nameField;
    @FXML
    private TextField skuField;
    @FXML
    private TextField sizeField;
    @FXML
    private Button submitForm;
    @FXML
    private TextField priceField;
    @FXML
    private TextField brandField;
    @FXML
    private Label addedText;
    @FXML
    private ComboBox<Double> sizeBox;
    @FXML
    private Button statsButton;
    @FXML
    private Button graphButton;
    @FXML
    private Button tableButton;
    @FXML
    private Button editDataButton;

    ObservableList<Shoe> oblist = FXCollections.observableArrayList();

    Connection con = DBConnect.getNewConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Establishing connection to the 1database
        try {
            con = DBConnect.getConnection();
            ResultSet rs = con.createStatement().executeQuery("SELECT * from jdbc.sneaker");

            while (rs.next()) {
                oblist.add(new Shoe(rs.getString("name"), rs.getString("brand"),rs.getString("sku"), rs.getDouble("size"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, e);
        }

        //Initializing table values
        col_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_sku.setCellValueFactory(new PropertyValueFactory<>("sku"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));

        shoeTable.setItems(oblist);

        //Setting sizes
        for (double i = 4; i < 13; i+= 0.5) {
            sizeBox.getItems().add(i);
        }
        sizeBox.setAccessibleText("Size");
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("hello-view.fxml", logout);
    }

    @FXML
    void setImportButton(ActionEvent event) {
        importButton.setOnAction(actionEvent -> {

        });
    }

        @FXML
    public void setSubmitForm(ActionEvent event) {
        // submitForm.setOnMouseClicked(mouseEvent -> {submitForm.setText("Clicked!"); addedText.setText("Test");});
        Shoe newShoe = new Shoe();
        submitForm.setOnMouseClicked(actionEvent -> {
            boolean allFieldsFilled = true;
            //Get text from fields
            TextField[] fieldsToSumbit = new TextField[]{nameField, skuField, brandField, priceField};
            //Check for empty fields
            for (TextField t: fieldsToSumbit) {
                if (t.getText().isEmpty()) {
                    allFieldsFilled = false;
                    t.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-radius: 2"); //Add red border
                    HBox.setMargin(t, new Insets(3, 0,0,5)); //change size to make it uniform
                }
//                else {
//                }
            }

            //Get size from sizeBox
            Double shoeSize = sizeBox.getValue();
            if (shoeSize != null) {
                newShoe.setSize(shoeSize);
            } else {
                sizeBox.setStyle("-fx-background-color: #d77878");
                allFieldsFilled = false; //don't allow user to submit
            }

            //Clear fields for more entries and send shoe to database
            if (allFieldsFilled) {
                newShoe.setSize(sizeBox.getValue());
                newShoe.setSku(skuField.getText());
                newShoe.setBrand(brandField.getText());
                newShoe.setName(nameField.getText());
                newShoe.setPrice(Double.parseDouble(priceField.getText()));
                try { //add shoe to the database
                    addShoeToDB(newShoe);
                } catch (SQLException e) {
                    throw new RuntimeException("Shoe could not be added", e);
                }

                brandField.clear(); nameField.clear(); priceField.clear();skuField.clear();
                sizeBox.setValue(null); sizeBox.setAccessibleText("Size");

                // Reset red borders for text boxes
                for (TextField t: fieldsToSumbit) {
                    t.setStyle("-fx-border-width: 0");
                    HBox.setMargin(t, new Insets(5, 0,5,5));
                }
                sizeBox.setStyle("-fx-background-color: white");
                sizeBox.setPromptText("Size");

            }
            //submitForm.setText("Shoe Added!");
            //System.out.println(newShoe);
        });
    }

    void addShoeToDB(Shoe shoe) throws SQLException {
        Statement state = con.createStatement();
        //ResultSet rows = state.executeQuery("select count(*) from sneaker");
//        int numRows = rows.getInt(1);
//        System.out.println(numRows);
        String insertStatement = shoe.addShoeToDB(27);
        state.executeUpdate(insertStatement);
        shoeTable.getItems().add(shoe);

        System.out.println("Successfully added " + shoe + " to the database!");
    }

    // Scene change buttons
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
    void setGraphButton(ActionEvent event) {
        HelloApplication h = new HelloApplication();
        graphButton.setOnMouseClicked(e -> {
            try {
                h.changeScene("graphPage.fxml", graphButton); //graph page not built yet
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editData(ActionEvent event) {
        editDataButton.setOnMouseClicked(e -> {
            Stage stage;
            try {
                Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("editTable.fxml")));
                stage = new Stage();
                stage.setScene(new Scene(pane));
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    void refreshTable (ActionEvent event) throws SQLException {
        oblist.clear();
        ResultSet rs = con.createStatement().executeQuery("SELECT * from jdbc.sneaker");

        while (rs.next()) {
            oblist.add(new Shoe(rs.getString("name"), rs.getString("brand"),rs.getString("sku"), rs.getDouble("size"), rs.getDouble("price")));
        }
    }

//    void deleteEntry(String shoe) {
//        shoeTable.getItems().indexOf(name)
//    }

//    @FXML
//    void closeApplication(ActionEvent event) {
//        exitButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                Platform.exit();
//            }
//        });
//    }
}


