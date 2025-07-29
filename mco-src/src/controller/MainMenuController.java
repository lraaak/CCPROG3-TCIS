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

/**
 * The {@code MainMenuController} class manages the main menu of the Trading Card Inventory System (TCIS).
 * It handles user interactions such as adding cards, decks, binders, and viewing the collection.
 * Additionally, it displays the current balance of the user's money in the TCIS system.
 */
public class MainMenuController {

    private TCIS tcis;

    @FXML
    private Button addCardButton;  // Button to add a new card
    @FXML
    private Button addDeckButton;  // Button to add a new deck
    @FXML
    private Button addBinderButton;  // Button to add a new binder
    @FXML
    private Button viewCollectionButton;  // Button to view the collection

    @FXML
    private Label moneyLabel;  // Label to display money

    /**
     * Setter for TCIS to pass the reference from the main application.
     * Updates the money label when TCIS is set.
     *
     * @param tcis The TCIS instance to set.
     */
    @FXML
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        updateMoneyLabel();  // Update the money display when the TCIS is set
    }

    /**
     * Updates the money label based on the current TCIS balance.
     * Displays the user's current money in a formatted way.
     */
    private void updateMoneyLabel() {
        double currentMoney = TCIS.getMoney();  // Get current money from TCIS
        moneyLabel.setText("Money: $" + String.format("%.2f", currentMoney));  // Update label with formatted money
    }

    /**
     * Handles the event when the "Add Card" button is clicked.
     * Loads the AddCard view and sets the TCIS reference.
     */
    @FXML
    public void handleAddCard () {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/add_card.fxml"));
            AnchorPane root = loader.load();

            // Get controller for AddCard view and pass the TCIS reference
            AddCardController addCardController = loader.getController();
            addCardController.setTCIS(tcis);

            // Get current window and set the AddCard scene
            Stage stage = (Stage) addCardButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event when the "Add Deck" button is clicked.
     * Currently, it just prints a message if TCIS is valid.
     */
    @FXML
    public void handleAddDeck() {
        if (tcis != null && tcis.isValid()) {
            System.out.println("Add Deck clicked.");
        } else {
            System.out.println("TCIS is not initialized properly.");
        }
    }

    /**
     * Handles the event when the "Add Binder" button is clicked.
     * Currently, it just prints a message if TCIS is valid.
     */
    @FXML
    public void handleAddBinder() {
        if (tcis != null && tcis.isValid()) {
            System.out.println("Add Binder clicked.");
        } else {
            System.out.println("TCIS is not initialized properly.");
        }
    }

    /**
     * Handles the event when the "View Collection" button is clicked.
     * Loads the ViewCollection view and sets the TCIS reference for the controller.
     */
    @FXML
    public void handleViewCollection() throws Exception {
        if (tcis != null && tcis.isValid()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/view_collection.fxml"));
                Parent root = loader.load();

                // Get controller for ViewCollection view and pass the TCIS reference
                ViewCollectionController viewController = loader.getController();
                if (viewController != null) {
                    viewController.setTCIS(tcis);

                    // Set the ViewCollection scene and show it
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
