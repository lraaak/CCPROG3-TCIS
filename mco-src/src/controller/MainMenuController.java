package controller;

import com.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
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
    private Button ManageDecksButton;
    @FXML
    private Button ManageBindersButton;

    @FXML
    private Label moneyLabel; // Label to display money

    @FXML
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        updateMoneyLabel();
        updateManageButtons();  // Update the visibility of buttons
    }

    // Update the money label based on current TCIS balance
    private void updateMoneyLabel() {
        double currentMoney = TCIS.getMoney(); // Get current money from TCIS
        moneyLabel.setText("Money: $" + String.format("%.2f", currentMoney)); // Update label with formatted money
    }

    private void updateManageButtons() {
        boolean hasDecks = !tcis.getDecks().isEmpty();
        boolean hasBinders = !tcis.getBinders().isEmpty();

        ManageDecksButton.setVisible(hasDecks);  // Only show if there are decks
        ManageBindersButton.setVisible(hasBinders);  // Only show if there are binders
    }

    @FXML
    public void handleAddCard() {
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/add_Deck.fxml"));
            AnchorPane root = loader.load();

            AddDeckController addDeckController = loader.getController();
            addDeckController.setTCIS(tcis);

            Stage stage = (Stage) addDeckButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddBinder() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/add_Binder.fxml"));
            AnchorPane root = loader.load();

            AddBinderController addBinderController = loader.getController();
            addBinderController.setTCIS(tcis);

            Stage stage = (Stage) addBinderButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
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

    @FXML
    public void handleManageDecks() throws Exception {
        if (tcis != null && tcis.isValid()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/manage_decks.fxml"));
                Parent root = loader.load();

                // Get the controller after loading the FXML
                ManageDecksController manageController = loader.getController();
                if (manageController != null) {
                    manageController.setTCIS(tcis);

                    Scene manageControllerScene = new Scene(root, 600, 400);
                    Stage primaryStage = (Stage) ManageDecksButton.getScene().getWindow();
                    primaryStage.setScene(manageControllerScene);
                    primaryStage.show();
                } else {
                    System.err.println("Failed to get ManageDeckScene");
                }
            } catch (Exception e) {
                System.err.println("Error loading manage_deck.fxml: " + e.getMessage());
                throw e;
            }
        }
    }

    @FXML
    public void handleManageBinders() throws Exception {
        if (tcis != null && tcis.isValid()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/manage_Binders.fxml"));
                Parent root = loader.load();

                // Get the controller after loading the FXML
                ManageBindersController manageController = loader.getController();
                if (manageController != null) {
                    manageController.setTCIS(tcis);

                    Scene manageControllerScene = new Scene(root, 600, 400);
                    Stage primaryStage = (Stage) ManageBindersButton.getScene().getWindow();
                    primaryStage.setScene(manageControllerScene);
                    primaryStage.show();
                } else {
                    System.err.println("Failed to get ManageDeckScene");
                }
            } catch (Exception e) {
                System.err.println("Error loading manage_deck.fxml: " + e.getMessage());
                throw e;
            }
        }
    }
}
