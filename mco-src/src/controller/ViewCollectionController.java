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

public class ViewCollectionController {

    private TCIS tcis;

    @FXML
    private ListView<String> collectionListView;

    @FXML
    private TextArea cardDetailsArea;

    @FXML
    private Label emptyMessage;


    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        refreshDisplay();
    }


    public void refreshDisplay() {
        if (tcis != null && tcis.isValid()) {
            ObservableList<String> cardList = FXCollections.observableArrayList();

            if (tcis.getCollection().getCard().isEmpty()) {
                emptyMessage.setVisible(true);
                collectionListView.setVisible(false);
            } else {

                tcis.getCollection().getCard().sort(Comparator.comparing(Cards::getName));


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


    @FXML
    public void handleBackToMainMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setTCIS(tcis);

            Stage stage = (Stage) collectionListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void increaseCardCount(ActionEvent event) {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];
            tcis.getCollection().increaseCardCount(cardName);
            showAlert(AlertType.INFORMATION, "Card Count Increased", "Card count increased by 1.");
            refreshDisplay();
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }

    // Handles decreasing the card count by 1
    @FXML
    public void decreaseCardCount(ActionEvent event) {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];
            tcis.getCollection().decreaseCardCount(cardName);
            showAlert(AlertType.INFORMATION, "Card Count Decreased", "Card count decreased by 1.");
            refreshDisplay();
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }

    @FXML
    public void showCardDetails() {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();
        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];

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

    @FXML
    public void sellCard(ActionEvent event) {
        String selectedCard = collectionListView.getSelectionModel().getSelectedItem();

        if (selectedCard != null) {
            String cardName = selectedCard.split(" \\(")[0];  // Extract the card name
            Cards card = Helper.findCard(cardName, tcis.getCollection().getCard());

            if (card != null) {

                double finalValue = card.getFinalValue();
                System.out.println("Card sold for: $" + finalValue);


                Collection.removeCard(card.getName());


                TCIS.addMoney(finalValue);


                showAlert(AlertType.INFORMATION, "Card Sold", "Card " + card.getName() + " has been sold for $" + finalValue);


                refreshDisplay();
            } else {
                showAlert(AlertType.WARNING, "Card Not Found", "Selected card is not in the collection.");
            }
        } else {
            showAlert(AlertType.WARNING, "No Card Selected", "Please select a card from the list.");
        }
    }



    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
