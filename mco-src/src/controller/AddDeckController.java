package controller;

import com.*;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

public class AddDeckController {

    private TCIS tcis;  // Declare TCIS to hold the reference

    @FXML
    private TextField myTextField;
    @FXML
    private ChoiceBox<String> myChoiceBox;

    // Setter for TCIS
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        System.out.println("TCIS object passed successfully to AddCardController.");
    }


    @FXML
    public void initialize() {

        myChoiceBox.getItems().addAll("Sellable", "Non-Sellable");

    }



    @FXML
    public void handleAddDeck() {
        String deckName = myTextField.getText().trim();
        String deckType = myChoiceBox.getValue();

        // Validate input
        if (deckName.isEmpty() || deckType == null) {
            showAlert(AlertType.ERROR, "Invalid Input", "All fields must be filled out.");
            return;
        }


        Decks existingDeck= Helper.findDeck(deckName, tcis.getDecks());
        if (existingDeck == null) {
            Decks deck;
            if (deckType.equalsIgnoreCase("Sellable")){
                deck = new SellableDeck(deckName);
            }
            else{
                deck = new Decks(deckName);
            }
            tcis.addDeck(deck);

            showAlert(AlertType.INFORMATION, "Deck has been added", "Deck "+deck.getName()+" has been added");
        } else {

            showAlert(AlertType.INFORMATION, "Add deck failed", "No deck has been added");
        }


        goBackToMainMenu();
    }



    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleBackToMainMenu() {
        goBackToMainMenu();
    }

    private void goBackToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setTCIS(tcis);

            Stage stage = (Stage) myTextField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
