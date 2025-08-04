package controller;

import com.*;

import java.io.IOException;
import java.util.*;

import com.Collection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageBindersController {

    private TCIS tcis;

    @FXML
    private ListView<Binders> bindersListView;

    @FXML
    private TextArea cardsTextArea;

    @FXML
    private ListView<String> cardsListView;

    @FXML
    private Button deleteBinderButton;

    @FXML
    private Button sellBinderButton;

    @FXML
    private Button addCardButton;

    @FXML
    private Button removeCardButton;

    @FXML
    private Button tradeCardButton;

    private ObservableList<Binders> binders;
    private Binders selectedBinder;

    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        loadBinders();
    }

    public void loadBinders() {
        binders = FXCollections.observableArrayList(tcis.getBinders());
        bindersListView.setItems(binders);

        addCardButton.setDisable(true);
        removeCardButton.setDisable(true);
        sellBinderButton.setVisible(false);
        tradeCardButton.setVisible(false);
    }

    @FXML
    public void showBinderDetails() {
        selectedBinder = bindersListView.getSelectionModel().getSelectedItem();

        if (selectedBinder != null) {
            StringBuilder sb = new StringBuilder();
            for (Cards card : selectedBinder.getCard()) {
                sb.append(card.toString()).append("\n");
            }
            cardsTextArea.setText(sb.toString());

            boolean sellable = selectedBinder instanceof Sellable;
            sellBinderButton.setVisible(sellable);
            tradeCardButton.setVisible(!sellable);
            addCardButton.setDisable(false);
            removeCardButton.setDisable(false);
        } else {
            cardsTextArea.clear();
            addCardButton.setDisable(true);
            removeCardButton.setDisable(true);
            sellBinderButton.setVisible(false);
            tradeCardButton.setVisible(false);
        }
    }

    @FXML
    public void deleteBinder() {
        if (HelperController.confirmAction("Delete binder", "Are you sure you want to delete this binder?")){
            tcis.deleteBinder(selectedBinder);
            binders.remove(selectedBinder);
            selectedBinder = null;
            sellBinderButton.setVisible(false);
            cardsTextArea.clear();

            if (binders.isEmpty()){
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

            Stage stage = (Stage) bindersListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void sellBinder() {
        if (selectedBinder instanceof Sellable) {
            boolean confirmed = HelperController.confirmAction("Confirm Sell", "Are you sure you want to sell this binder?");
            if (!confirmed) return;

            double value = ((Sellable) selectedBinder).getSaleValue();

            HelperController.showAlert("Binder Sold","Binder sold for: $" + String.format("%.2f", value));

            binders.remove(selectedBinder);
            tcis.getBinders().remove(selectedBinder);
            TCIS.addMoney(value);
            selectedBinder = null;
            sellBinderButton.setVisible(false);
            cardsTextArea.clear();

            if (binders.isEmpty()){
                handleBackToMainMenu();
            }
        }
    }

    @FXML
    public void tradeCard() {
        if (selectedBinder == null) return;

        ObservableList<Cards> binderCards = FXCollections.observableArrayList(selectedBinder.getCard());
        if (binderCards.isEmpty()) {
            HelperController.showAlert("No Cards", "There are no cards in the binder to trade.");
            return;
        }

        // Step 1: User chooses a card from the binder to trade away
        ChoiceDialog<Cards> choiceDialog = new ChoiceDialog<>(binderCards.getFirst(), binderCards);
        choiceDialog.setTitle("Choose Card to Trade");
        choiceDialog.setHeaderText("Select a card from your binder to trade");
        choiceDialog.setContentText("Your cards:");

        Optional<Cards> result = choiceDialog.showAndWait();
        if (result.isEmpty()) return;

        Cards ownCard = result.get();
        int ownCardIndex = selectedBinder.getCard().indexOf(ownCard);

        try {
            // Step 2: Load AddCard scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/add_TradeCard.fxml"));
            Parent root = loader.load();

            AddCardController controller = loader.getController();
            controller.setTCIS(tcis);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Card to Receive");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

            Cards otherCard = controller.cardCreated;

            if (otherCard == null) {
                HelperController.showAlert("Trade Cancelled", "No card was received.");
                return;
            }
            boolean proceed = true;
            if (selectedBinder instanceof CollectorBinder) {
                proceed = (otherCard.getRarity().equalsIgnoreCase("RARE") ||
                        otherCard.getRarity().equalsIgnoreCase("LEGENDARY")) &&
                        !otherCard.getVariant().equalsIgnoreCase("NORMAL");
            }

                if (proceed) {
                    double valueDifference = Math.abs(ownCard.getFinalValue() - otherCard.getFinalValue());
                    String msg = "Trade " + ownCard.getName() + " for " + otherCard.getName() + "?";
                    if (valueDifference >= 1.0) {
                        msg += "\nNote: Value difference is $" + String.format("%.2f", valueDifference);
                    }

                    boolean confirm = HelperController.confirmAction("Confirm Trade", msg);
                    if (confirm) {
                        selectedBinder.getCard().set(ownCardIndex, otherCard);
                        HelperController.showAlert("Trade Complete", ownCard.getName() + " was traded for " + otherCard.getName());
                        showBinderDetails(); // Refresh view
                    } else {
                        HelperController.showAlert("Cancelled", "Trade was cancelled.");
                    }
                }
        }catch(IOException e){
            e.printStackTrace();
    }
        }



    @FXML
    public void handleAddCardToBinder(ActionEvent event) {
        if (selectedBinder == null) return;

        var collectionCards = tcis.getCollection().getCard();

        if (collectionCards.isEmpty()) {
            HelperController.showAlert("No Cards", "You have no cards in your collection.");
            return;
        }

        if (selectedBinder.getCard().size() >= 20) {
            HelperController.showAlert("Binder Full", "This binder already has the maximum number of cards.");
            return;
        }

        ChoiceDialog<Cards> dialog = new ChoiceDialog<>(collectionCards.getFirst(), collectionCards);
        dialog.setTitle("Add Card to Binder");
        dialog.setHeaderText("Select a card to add to the binder");
        dialog.setContentText("Choose a card:");

        dialog.showAndWait().ifPresent(selectedCard -> {
            if (selectedBinder.getCard().contains(selectedCard)) {
                HelperController.showAlert("Card Exists", "This card is already in the binder.");
            } else {
                if (HelperController.confirmAction("Adding Card", "Are you sure you want to add " + selectedCard.getName()+ " to " + selectedBinder.getName())) {
                    selectedBinder.addCard(selectedCard);
                }
                showBinderDetails();
            }
        });
    }

    @FXML
    public void handleRemoveCardFromBinder() {
        if (selectedBinder == null || selectedBinder.getCard().isEmpty()) return;

        ChoiceDialog<Cards> dialog = new ChoiceDialog<>(selectedBinder.getCard().getFirst(), selectedBinder.getCard());
        dialog.setTitle("Remove Card from Binder");
        dialog.setHeaderText("Select a card to remove from the binder");
        dialog.setContentText("Choose a card:");

        dialog.showAndWait().ifPresent(card -> {
            boolean confirmed = HelperController.confirmAction("Confirm Remove Card", "Are you sure you want to remove this card?");
            if (confirmed) {
                selectedBinder.removeCard(card);
                showBinderDetails();
            }
        });
    }
}

