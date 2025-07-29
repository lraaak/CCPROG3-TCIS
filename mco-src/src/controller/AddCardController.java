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

/**
 * The {@code AddCardController} class handles the interaction for adding a new card to the collection in the TCIS (Trading Card Inventory System).
 * It manages the user's input, including card name, rarity, variant, and value, and processes the addition or update of a card in the collection.
 */
public class AddCardController {

    private TCIS tcis;  // Declare TCIS to hold the reference

    @FXML
    private TextField cardNameField;  // Input field for card name
    @FXML
    private ChoiceBox<String> rarityChoiceBox;  // ChoiceBox for selecting the rarity of the card
    @FXML
    private ChoiceBox<String> variantChoiceBox;  // ChoiceBox for selecting the variant of the card
    @FXML
    private TextField valueField;  // Input field for card value

    /**
     * Setter for TCIS to pass the reference from the main application.
     *
     * @param tcis The TCIS instance to set.
     */
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        System.out.println("TCIS object passed successfully to AddCardController.");
    }

    /**
     * Initializes the components of the AddCard form.
     * Populates the ChoiceBoxes with predefined values for rarity and variant.
     * Sets the default visibility of the variantChoiceBox based on the rarity selected.
     */
    @FXML
    public void initialize() {
        // Populate Rarity ChoiceBox with values
        rarityChoiceBox.getItems().addAll("Common", "Uncommon", "Rare", "Legendary");

        // Set default value for Rarity
        rarityChoiceBox.setValue("Common");  // Default to "Common"

        // Populate Variant ChoiceBox with values
        variantChoiceBox.getItems().addAll("Normal", "Extended-Art", "Full-Art", "Alt-Art");

        // Set default value for Variant
        variantChoiceBox.setValue("Normal");  // Default to "Normal"

        // Set default visibility of Variant ChoiceBox
        variantChoiceBox.setVisible(false);

        // Set action listener for Rarity ChoiceBox
        rarityChoiceBox.setOnAction(this::updateVariantVisibility);

        updateVariantVisibility(null);  // Initialize visibility based on the current rarity selection
    }

    /**
     * Updates the visibility of the Variant ChoiceBox based on the selected Rarity.
     * If the rarity is "Rare" or "Legendary", the variantChoiceBox becomes visible.
     * Otherwise, it is hidden.
     *
     * @param event The action event triggered when the Rarity selection changes.
     */
    private void updateVariantVisibility(ActionEvent event) {
        // Show Variant ChoiceBox if "Rare" or "Legendary" is selected
        if ("Rare".equals(rarityChoiceBox.getValue()) || "Legendary".equals(rarityChoiceBox.getValue())) {
            variantChoiceBox.setVisible(true);
        } else {
            variantChoiceBox.setVisible(false);
        }
    }

    /**
     * Handles the event when the "Add Card" button is clicked.
     * Validates the user's input, creates a new card or updates an existing card in the collection.
     * Displays a success or error message depending on the outcome.
     */
    @FXML
    public void handleAddCard() {
        String cardName = cardNameField.getText().trim();
        String rarity = rarityChoiceBox.getValue();
        String variant = variantChoiceBox.getValue();
        String valueStr = valueField.getText().trim();

        // Validate input
        if (cardName.isEmpty() || rarity == null || (rarity.equals("Rare") || rarity.equals("Legendary")) && variant == null || valueStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Invalid Input", "All fields must be filled out.");
            return;
        }

        // Set variant to "Normal" if rarity is "Common" or "Uncommon"
        if (rarity.equals("Common") || rarity.equals("Uncommon")) {
            variant = "Normal";
        }

        // Validate value as a double
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

        // Check if the card already exists in the collection
        Cards existingCard = Helper.findCard(cardName, tcis.getCollection().getCard());
        if (existingCard != null && existingCard.getRarity().equals(rarity) && existingCard.getVariant().equals(variant)) {
            // Increase the count if the card exists
            tcis.getCollection().increaseCardCount(cardName);
            showAlert(AlertType.INFORMATION, "Card Count Increased", "Card count increased by 1.");
        } else {
            // If the card doesn't exist, create a new card and add it to the collection
            Cards newCard = new Cards(cardName, rarity.toUpperCase(), variant.toUpperCase(), value);
            Collection.addCard(newCard.getName(), newCard.getRarity(), newCard.getVariant(), newCard.getBaseValue());
            showAlert(AlertType.INFORMATION, "Success", newCard.getName() + " | " + newCard.getRarity() + " | " + newCard.getVariant() + " | $" + newCard.getFinalValue() + " added to the collection.");
        }

        // Go back to the Main Menu after successful addition
        goBackToMainMenu();
    }

    /**
     * Displays an alert with the given type, title, and message.
     *
     * @param alertType The type of alert to show (e.g., INFORMATION, ERROR).
     * @param title The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Handles the "Back to Main Menu" button click event.
     * Navigates the user back to the main menu.
     */
    @FXML
    public void handleBackToMainMenu() {
        goBackToMainMenu();
    }

    /**
     * Navigates the user back to the main menu.
     */
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
}
