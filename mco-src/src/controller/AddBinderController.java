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

public class AddBinderController {

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
        myChoiceBox.getItems().addAll("Non-Curated(Trade-able)", "Collector Binder(Trade-able)",
                "Pauper Binder(Sellable)", "Rares Binder(Sellable)", "Luxury Binder(Sellable)");
    }



    @FXML
    public void handleAddBinder() {
        String binderName = myTextField.getText().trim();
        String binderType = myChoiceBox.getValue();

        // Validate input
        if (binderName.isEmpty() || binderType == null) {
            HelperController.showAlert("Invalid Input", "All fields must be filled out.");
            return;
        }


        Binders existingBinder= Helper.findBinder(binderName, tcis.getBinders());
        if (existingBinder == null) {
            Binders binder = null;
            if (binderType.equalsIgnoreCase("Non-Curated(Trade-able)")) {
                binder = new NonCuratedBinder(binderName);
            } else if (binderType.equalsIgnoreCase("Collector Binder(Trade-able)")) {
                binder = new CollectorBinder(binderName);
            } else if (binderType.equalsIgnoreCase("Pauper Binder(Sellable)")) {
                binder = new PauperBinder(binderName);
            } else if (binderType.equalsIgnoreCase("Rares Binder(Sellable)")) {
                binder = new RaresBinder(binderName);
            } else if (binderType.equalsIgnoreCase("Luxury Binder(Sellable)")) {
                binder = new LuxuryBinder(binderName);
            }

            tcis.addBinder(binder);

            HelperController.showAlert( "Binder has been added", "Binder "+binder.getName()+" has been added");
        } else {

           HelperController.showAlert("Add binder failed", "Binder already exists");
        }

        goBackToMainMenu();
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
