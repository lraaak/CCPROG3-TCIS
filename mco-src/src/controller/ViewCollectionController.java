package controller;

import com.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.util.Collections;
import java.util.Comparator;

/**
 * The {@code ViewCollectionController} class is responsible for managing and displaying the user's collection of trading cards in the TCIS (Trading Card Inventory System).
 * It allows for viewing the collection, modifying the count of cards, viewing details about selected cards, and selling cards from the collection.
 */
public class ViewCollectionController {

    private TCIS tcis;

    @FXML
    private ListView<String> collectionListView;  // ListView to display the cards in the collection

    @FXML
    private TextArea cardDetailsArea;  // TextArea to show the details of the selected card

    @FXML
    private Label emptyMessage;  // Label to indicate when the collection is empty


    /**
     * Setter method for the TCIS object. It updates the collection and triggers a display refresh.
     *
     * @param tcis the TCIS object containing the collection data
     */
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        refreshDisplay();
    }


    /**
     * Refreshes the collection display by fetching the latest data from TCIS.
     * It updates the ListView with card names, sorts them, and displays the empty message if the collection is empty.
     */
    public void refreshDisplay() {
        if (tcis != null && tcis.isValid()) {
            ObservableList<String> cardList = FXCollections.observableArrayList();

            if (tcis.getCollection().getCard().isEmpty()) {
                emptyMessage.setVisible(true);
                collectionListView.setVisible(false);
            } else {

                tcis.getCollection().getCard().sort(Comparator.comparing(Cards::getName));  // Sort cards alphabetically


                for (Cards card : tcis.getCollection().getCard()) {
                    cardList.add(card.getName() + " (" + card.getRarity() + ") - Count: " + card.getSelfCount());
                }

                collectionListView.setItems(cardList);
                collectionListView.setVisible(true);
                emptyMessage.setVisible(false);
            }
        } else {
            emptyMessage.setText("Collection unavailable.");
            emptyMessage.setVisible(true);
            collectionListView.setVisible(false);
        }
    }


    /**
     * Handles the event when the user clicks the "Back to Main Menu" button.
     * It loads the main menu and passes the TCIS reference to the MainMenuController.
     *
     * @param event the event triggered by the button click
     */
    @FXML
    public void handleBackToMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setTCIS(tcis);  // Pass the TCIS reference

            Stage stage = (Stage) collectionListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Handles the event when the user clicks the "Increase Card Count" button.
     * It increases the count of the selected card in the collection by 1.
     *
     * @param event the event triggered by the button click
     */
    @FXML
    public void increaseCardCount(ActionEvent event) {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];  // Extract card name
            tcis.getCollection().increaseCardCount(cardName);
            showAlert(AlertType.INFORMATION, "Card Count Increased", "Card count increased by 1.");
            refreshDisplay();  // Refresh the collection display
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }

    /**
     * Handles the event when the user clicks the "Decrease Card Count" button.
     * It decreases the count of the selected card in the collection by 1.
     *
     * @param event the event triggered by the button click
     */
    @FXML
    public void decreaseCardCount(ActionEvent event) {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];  // Extract card name
            tcis.getCollection().decreaseCardCount(cardName);
            showAlert(AlertType.INFORMATION, "Card Count Decreased", "Card count decreased by 1.");
            refreshDisplay();  // Refresh the collection display
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }

    /**
     * Displays the details of the selected card in the TextArea.
     *
     */
    @FXML
    public void showCardDetails() {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];  // Extract card name

            // Find the card in the collection
            Cards card = Helper.findCard(cardName, tcis.getCollection().getCard());
            if (card != null) {
                String details = "Name: " + card.getName() + "\n"
                        + "Rarity: " + card.getRarity() + "\n"
                        + "Variant: " + card.getVariant() + "\n"
                        + "Value: $" + card.getFinalValue() + "\n"
                        + "Count: " + card.getSelfCount();
                cardDetailsArea.setText(details);
            }
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }

    /**
     * Handles the event when the user clicks the "Sell Card" button.
     * It sells the selected card and adds its value to the user's total money.
     *
     * @param event the event triggered by the button click
     */
    @FXML
    public void sellCard(ActionEvent event) {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();

        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];  // Extract the card name
            Cards card = Helper.findCard(cardName, tcis.getCollection().getCard());

            if (card != null) {

                double finalValue = card.getFinalValue();
                System.out.println("Card sold for: $" + finalValue);

                // Remove the card from the collection
                Collection.removeCard(card.getName());

                // Add money from the card sale to the TCIS balance
                TCIS.addMoney(finalValue);

                showAlert(AlertType.INFORMATION, "Card Sold", "Card " + card.getName() + " has been sold for $" + finalValue);

                refreshDisplay();  // Refresh the collection display
            } else {
                showAlert(AlertType.WARNING, "Card Not Found", "Selected card is not in the collection.");
            }
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }

    /**
     * Displays an alert with the given type, title, and message.
     *
     * @param alertType the type of the alert (e.g., ERROR, INFORMATION)
     * @param title the title of the alert
     * @param message the content message of the alert
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
