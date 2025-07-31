package controller;

import com.*;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class MainMenuController {

    private TCIS tcis;

    @FXML
    private Button addCardButton;
    @FXML
    private Button addDeckButton;
    @FXML
    private Button addBinderButton;
    @FXML
    private Button viewCollectionButton;

    @FXML
    private Label moneyLabel; // Label to display money

    @FXML
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        updateMoneyLabel();  // Update the money display when the TCIS is set
    }

    // Update the money label based on current TCIS balance
    private void updateMoneyLabel() {
        double currentMoney = TCIS.getMoney(); // Get current money from TCIS
        moneyLabel.setText("Money: $" + String.format("%.2f", currentMoney)); // Update label with formatted money
    }

    @FXML
    public void handleAddCard () {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/add_card.fxml"));
            AnchorPane root = loader.load();


            AddCardController addCardController = loader.getController();
            addCardController.setTCIS(tcis);


            Stage stage = (Stage) addCardButton.getScene().getWindow();


            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddDeck() {
        if (tcis != null && tcis.isValid()) {
            System.out.println("Add Deck clicked.");
        } else {
            System.out.println("TCIS is not initialized properly.");
        }
    }

    @FXML
    public void handleAddBinder() {
        if (tcis != null && tcis.isValid()) {
            System.out.println("Add Binder clicked.");
        } else {
            System.out.println("TCIS is not initialized properly.");
        }
    }

    @FXML
    public void handleViewCollection() throws Exception {
        if (tcis != null && tcis.isValid()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view_collection.fxml"));
                Parent root = loader.load();

                // Get the controller after loading the FXML
                ViewCollectionController viewController = loader.getController();
                if (viewController != null) {
                    viewController.setTCIS(tcis);

                    Scene viewCollectionScene = new Scene(root, 600, 400);
                    Stage primaryStage = (Stage) viewCollectionButton.getScene().getWindow();
                    primaryStage.setScene(viewCollectionScene);
                    primaryStage.show();
                } else {
                    System.err.println("Failed to get ViewCollectionController");
                }
            } catch (Exception e) {
                System.err.println("Error loading view_collection.fxml: " + e.getMessage());
                throw e;
            }
        }
    }

}