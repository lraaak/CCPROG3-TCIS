package controller;

import com.*;

import java.io.IOException;
import java.util.*;

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
    private Button sellBinderButton;

    @FXML
    private Button addCardButton;

    @FXML
    private Button removeCardButton;

    @FXML
    private Button tradeCardButton;

    @FXML
    private TextField customPriceTextField; // TextField for custom price input
    @FXML
    private Button setCustomPriceButton;   // Button to set the custom price

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
        setCustomPriceButton.setVisible(false); // Hide custom price field and button by default
        customPriceTextField.setVisible(false);
    }

    @FXML
    public void showBinderDetails() {
        selectedBinder = bindersListView.getSelectionModel().getSelectedItem();

        if (selectedBinder != null) {
            StringBuilder sb = new StringBuilder();

            // Show the binder type (LuxuryBinder, PauperBinder, etc.)
            String binderType = "Binder Type: " + selectedBinder.getClass().getSimpleName();
            sb.append(binderType).append("\n\n");

            // List the cards in the selected binder
            for (Cards card : selectedBinder.getCard()) {
                sb.append(card.toString()).append("\n");
            }

            // Set the text in the cardsTextArea
            cardsTextArea.setText(sb.toString());

            // Determine if the binder is sellable
            boolean sellable = selectedBinder instanceof Sellable;
            sellBinderButton.setVisible(sellable);
            tradeCardButton.setVisible(!sellable);
            addCardButton.setDisable(false);
            removeCardButton.setDisable(false);

            // Show custom price options for LuxuryBinders
            if (selectedBinder instanceof LuxuryBinder) {
                setCustomPriceButton.setVisible(true);
                customPriceTextField.setVisible(true); // Show custom price input for LuxuryBinder
            } else {
                setCustomPriceButton.setVisible(false);
                customPriceTextField.setVisible(false); // Hide for other binders
            }
        } else {
            // Clear everything when no binder is selected
            cardsTextArea.clear();
            addCardButton.setDisable(true);
            removeCardButton.setDisable(true);
            sellBinderButton.setVisible(false);
            tradeCardButton.setVisible(false);
            setCustomPriceButton.setVisible(false);
            customPriceTextField.setVisible(false);
        }
    }


    @FXML
    public void setCustomPrice(ActionEvent event) {
        if (selectedBinder instanceof LuxuryBinder) {
            // Check if the binder has any cards
            if (selectedBinder.getCard().isEmpty()) {
                HelperController.showAlert("No Cards", "Cannot modify the price. The binder has no cards.");
                return;
            }

            try {
                double customPrice = Double.parseDouble(customPriceTextField.getText());

                if (customPrice <= 0) {
                    HelperController.showAlert("Invalid Input", "Please enter a valid price greater than 0.");
                    return;
                }

                double minPrice = ((LuxuryBinder) selectedBinder).getCardValue(); // Get the minimum price of the cards in the binder

                // Only set the custom price if it is valid
                if (customPrice > minPrice) {
                    ((LuxuryBinder) selectedBinder).setCustomPrice(customPrice); // Set the custom price

                    // Show the alert only when the price is actually set
                    HelperController.showAlert("Price Set", "Custom price set to $" + customPrice);
                } else {
                    // Show an error if the price is not valid
                    HelperController.showAlert("Invalid Price", "Custom price must be greater than the minimum value of the cards in the binder ($" + minPrice + ").");
                }
            } catch (NumberFormatException e) {
                // Show an error if the input is not a valid number
                HelperController.showAlert("Invalid Input", "Please enter a valid number for the price.");
            }
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

            // Step 3: Check if the incoming card meets the binder restrictions
            if (selectedBinder instanceof CollectorBinder) {
                if (!(otherCard.getRarity().equalsIgnoreCase("RARE") || otherCard.getRarity().equalsIgnoreCase("LEGENDARY")) || otherCard.getVariant().equalsIgnoreCase("NORMAL")) {
                    HelperController.showAlert("Invalid Card", "Card must be either RARE or LEGENDARY and not NORMAL for Collector Binder.");
                    proceed = false;
                }
            } else if (selectedBinder instanceof LuxuryBinder) {
                if (otherCard.getVariant().equalsIgnoreCase("NORMAL")) {
                    HelperController.showAlert("Invalid Card", "Card must have a variant other than NORMAL for Luxury Binder.");
                    proceed = false;
                }
            } else if (selectedBinder instanceof PauperBinder) {
                if (!(otherCard.getRarity().equalsIgnoreCase("COMMON") || otherCard.getRarity().equalsIgnoreCase("UNCOMMON"))) {
                    HelperController.showAlert("Invalid Card", "Card must be either COMMON or UNCOMMON for Pauper Binder.");
                    proceed = false;
                }
            }

            // Step 4: Proceed with the trade if all restrictions are met
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

        } catch (IOException e) {
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
                boolean isValid = false;

                // Check if the card meets the restrictions for this binder
                if (selectedBinder instanceof LuxuryBinder) {
                    if (!selectedCard.getVariant().equalsIgnoreCase("NORMAL")) {
                        isValid = true;
                    } else {
                        HelperController.showAlert("Invalid Card", "Card must have a variant other than NORMAL for Luxury Binder.");
                    }
                } else if (selectedBinder instanceof PauperBinder) {
                    if (selectedCard.getRarity().equalsIgnoreCase("COMMON") || selectedCard.getRarity().equalsIgnoreCase("UNCOMMON")) {
                        isValid = true;
                    } else {
                        HelperController.showAlert("Invalid Card", "Card must be either COMMON or UNCOMMON for Pauper Binder.");
                    }
                } else if (selectedBinder instanceof CollectorBinder) {
                    if ((selectedCard.getRarity().equalsIgnoreCase("RARE") || selectedCard.getRarity().equalsIgnoreCase("LEGENDARY"))
                            && !selectedCard.getVariant().equalsIgnoreCase("NORMAL")) {
                        isValid = true;
                    } else {
                        HelperController.showAlert("Invalid Card", "Card must be either RARE or LEGENDARY and not NORMAL for Collector Binder.");
                    }
                } else {
                    // For other binders, assume it's valid to add the card
                    isValid = true;
                }

                // If valid, add the card to the binder
                if (isValid) {
                    if (HelperController.confirmAction("Adding Card", "Are you sure you want to add " + selectedCard.getName() + " to " + selectedBinder.getName())) {
                        selectedBinder.addCard(selectedCard);
                    }
                }

                showBinderDetails(); // Refresh binder details after card is added
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
