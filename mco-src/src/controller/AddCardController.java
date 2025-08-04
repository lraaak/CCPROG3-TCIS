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

public class AddCardController {

    private TCIS tcis;
    public Cards cardCreated; // public or provide a getter

    @FXML
    private TextField cardNameField;
    @FXML
    private ChoiceBox<String> rarityChoiceBox;
    @FXML
    private ChoiceBox<String> variantChoiceBox;
    @FXML
    private TextField valueField;

    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
    }

    @FXML
    public void initialize() {
        rarityChoiceBox.getItems().addAll("Common", "Uncommon", "Rare", "Legendary");
        rarityChoiceBox.setValue("Common");

        variantChoiceBox.getItems().addAll("Normal", "Extended-Art", "Full-Art", "Alt-Art");
        variantChoiceBox.setValue("Normal");
        variantChoiceBox.setVisible(false);

        rarityChoiceBox.setOnAction(this::updateVariantVisibility);
        updateVariantVisibility(null);
    }

    private void updateVariantVisibility(ActionEvent event) {
        if ("Rare".equals(rarityChoiceBox.getValue()) || "Legendary".equals(rarityChoiceBox.getValue())) {
            variantChoiceBox.setVisible(true);
        } else {
            variantChoiceBox.setVisible(false);
        }
    }

    @FXML
    public void handleTradeCard() {
        setTCIS(tcis);
        String cardName = cardNameField.getText().trim();
        String rarity = rarityChoiceBox.getValue();
        String variant = variantChoiceBox.getValue();
        String valueStr = valueField.getText().trim();

        if (cardName.isEmpty() || rarity == null || (rarity.equals("Rare") || rarity.equals("Legendary")) && variant == null || valueStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Invalid Input", "All fields must be filled out.");
            return;
        }

        if (rarity.equals("Common") || rarity.equals("Uncommon")) {
            variant = "Normal";
        }

        double value;
        try {
            value = Double.parseDouble(valueStr);
            if (value <= 0) {
                showAlert(AlertType.ERROR, "Invalid Value", "Value must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Value", "Value must be a valid number.");
            return;
        }

        // Create the card and store it
        this.cardCreated = new Cards(cardName, rarity.toUpperCase(), variant.toUpperCase(), value);

        closeWindow();
    }

    @FXML
    public void handleAddCard() {
        setTCIS(tcis);
        String cardName = cardNameField.getText().trim();
        String rarity = rarityChoiceBox.getValue();
        String variant = variantChoiceBox.getValue();
        String valueStr = valueField.getText().trim();

        // Validate input
        if (cardName.isEmpty() || rarity == null || (rarity.equals("Rare") || rarity.equals("Legendary")) && variant == null || valueStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Invalid Input", "All fields must be filled out.");
            return;
        }


        if (rarity.equals("Common") || rarity.equals("Uncommon")) {
            variant = "Normal";
        }


        double value;
        try {
            value = Double.parseDouble(valueStr);
            if (value <= 0) {
                showAlert(AlertType.ERROR, "Invalid Value", "Value must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Invalid Value", "Value must be a valid number.");
            return;
        }

        Cards existingCard = Helper.findCard(cardName, tcis.getCollection().getCard());
        if (existingCard != null && existingCard.getRarity().equals(rarity) && existingCard.getVariant().equals(variant)) {

            tcis.getCollection().increaseCardCount(cardName);
            showAlert(AlertType.INFORMATION, "Card Count Increased", "Card count increased by 1.");
        } else {
            Cards newCard = new Cards(cardName, rarity.toUpperCase(), variant.toUpperCase(), value);
            cardCreated = newCard;
            Collection.addCard(newCard.getName(), newCard.getRarity(), newCard.getVariant(), newCard.getBaseValue());
            showAlert(AlertType.INFORMATION, "Success", newCard.getName() + " | " + newCard.getRarity() + " | " + newCard.getVariant() + " | $" + newCard.getFinalValue() + " added to the collection.");
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

            Stage stage = (Stage) cardNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWindow(){
        Stage stage = (Stage) cardNameField.getScene().getWindow();
        stage.close();
    }
}


