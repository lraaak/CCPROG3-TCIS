package controller;

import com.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
    }

    @FXML
    public void handleAddCard() {
        if (tcis != null && tcis.isValid()) {
            System.out.println("Add Card clicked.");
        } else {
            System.out.println("TCIS is not initialized properly.");
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

    public void handleBackToMainMenu(ActionEvent event) {
    }
}