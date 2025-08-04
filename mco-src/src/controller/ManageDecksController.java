package controller;

import com.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ManageDecksController {

    private TCIS tcis;

    @FXML
    private ListView<Decks> deckListView;

    @FXML
    private TextArea cardsTextArea;

    @FXML
    private Button sellDeckButton;

    @FXML
    private Button addCardButton;

    @FXML
    private Button removeCardButton;

    private ObservableList<Decks> decks;
    private Decks selectedDeck;

    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        loadDecks();
    }

    public void loadDecks() {
        decks = FXCollections.observableArrayList(tcis.getDecks());
        deckListView.setItems(decks);
        showDeckDetails();

        addCardButton.setDisable(true);
        removeCardButton.setDisable(true);
        sellDeckButton.setVisible(false);
    }

    @FXML
    public void showDeckDetails() {
        selectedDeck = deckListView.getSelectionModel().getSelectedItem();

        if (selectedDeck != null) {
            StringBuilder sb = new StringBuilder();

            // Check if it's a SellableDeck and show its total value
            if (selectedDeck instanceof SellableDeck) {
                SellableDeck sellableDeck = (SellableDeck) selectedDeck;
                double totalDeckValue = sellableDeck.getSaleValue();
                sb.append("Deck Sale Value: $").append(String.format("%.2f", totalDeckValue)).append("\n\n");
            }

            // Display card details including price for each card
            for (Cards card : selectedDeck.getCard()) {
                sb.append(card.toString())
                        .append(" - Price: $")
                        .append(String.format("%.2f", card.getFinalValue()))
                        .append("\n");
            }

            cardsTextArea.setText(sb.toString());

            sellDeckButton.setVisible(selectedDeck instanceof Sellable);
            addCardButton.setDisable(false);
            removeCardButton.setDisable(false);
        } else {
            cardsTextArea.clear();
            addCardButton.setDisable(true);
            removeCardButton.setDisable(true);
            sellDeckButton.setVisible(false);
        }
    }


    @FXML
    public void deleteDeck() {
            if (HelperController.confirmAction("Delete deck", "Are you sure you want to delete this deck?")){
            tcis.deleteDeck(selectedDeck);
            decks.remove(selectedDeck);
            selectedDeck = null;
            sellDeckButton.setVisible(false);
            cardsTextArea.clear();

            if (decks.isEmpty()){
                handleBackToMainMenu();
            }
        }
    }

    @FXML
    public void handleBackToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setTCIS(tcis);

            Stage stage = (Stage) deckListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sellDeck() {
        if (selectedDeck instanceof Sellable) {
            boolean confirmed = HelperController.confirmAction("Confirm Sell", "Are you sure you want to sell this deck?");
            if (!confirmed) return;

            double value = ((Sellable) selectedDeck).getSaleValue();

            HelperController.showAlert("Deck Sold","Deck sold for: $" + String.format("%.2f", value) );

            decks.remove(selectedDeck);
            tcis.getDecks().remove(selectedDeck);
            TCIS.addMoney(((Sellable) selectedDeck).getSaleValue());
            selectedDeck = null;
            sellDeckButton.setVisible(false);
            cardsTextArea.clear();

            if (decks.isEmpty()){
                handleBackToMainMenu();
            }
        }
    }

    @FXML
    public void handleAddCardToDeck(ActionEvent event) {
        if (selectedDeck == null) return;

        var collectionCards = tcis.getCollection().getCard();

        if (collectionCards.isEmpty()) {
            HelperController.showAlert("No Cards", "You have no cards in your collection.");
            return;
        }

        if (selectedDeck.getCard().size() >= 10) {
            HelperController.showAlert("Deck Full", "This deck already has the maximum number of cards.");
            return;
        }

        ChoiceDialog<Cards> dialog = new ChoiceDialog<>(collectionCards.getFirst(), collectionCards);
        dialog.setTitle("Add Card to Deck");
        dialog.setHeaderText("Select a card to add to the deck");
        dialog.setContentText("Choose a card:");

        dialog.showAndWait().ifPresent(selectedCard -> {
            if (selectedDeck.getCard().contains(selectedCard)) {
                HelperController.showAlert("Card Exists", "This card is already in the deck.");
            } else {
                if (HelperController.confirmAction("Adding Card", "Are you sure you want to add " + selectedCard.getName()+ " to " + selectedDeck.getName())) {
                    selectedDeck.addCard(selectedCard);
                }
                    showDeckDetails();
            }
        });
    }

    @FXML
    public void handleRemoveCardFromDeck() {
        if (selectedDeck == null || selectedDeck.getCard().isEmpty()) return;

        ChoiceDialog<Cards> dialog = new ChoiceDialog<>(selectedDeck.getCard().getFirst(), selectedDeck.getCard());
        dialog.setTitle("Remove Card from Deck");
        dialog.setHeaderText("Select a card to remove from the deck");
        dialog.setContentText("Choose a card:");

        dialog.showAndWait().ifPresent(card -> {
            boolean confirmed = HelperController.confirmAction("Confirm Remove Card", "Are you sure you want to remove this card?");
            if (confirmed) {
                selectedDeck.removeCard(card);
                showDeckDetails();
            }
        });
    }
}




