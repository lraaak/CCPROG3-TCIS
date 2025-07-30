package controller;

import com.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class ViewCollectionController {

    private TCIS tcis;

    @FXML
    private ListView<String> collectionListView;

    @FXML
    private Label emptyMessage;

    // Setter for TCIS and triggers UI refresh
    public void setTCIS(TCIS tcis) {
        this.tcis = tcis;
        refreshDisplay(); // Refresh UI after setting TCIS
    }

    // Use this instead of initialize for dynamic updates
    public void refreshDisplay() {
        if (tcis != null && tcis.isValid()) {
            ObservableList<String> cardList = FXCollections.observableArrayList();

            if (tcis.getCollection().getCard().isEmpty()) {
                emptyMessage.setVisible(true);
                collectionListView.setVisible(false);
            } else {
                for (Cards card : tcis.getCollection().getCard()) {
                    cardList.add(card.getName() + " (" + card.getRarity() + ")");
                }
                collectionListView.setItems(cardList);
                collectionListView.setVisible(true);
                emptyMessage.setVisible(false);
            }
        } else {
            System.out.println("TCIS is not initialized properly.");
            emptyMessage.setText("Collection unavailable.");
            emptyMessage.setVisible(true);
            collectionListView.setVisible(false);
        }
    }

    // Handles going back to main menu
    @FXML
    public void handleBackToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
            Parent root = loader.load();

            // Retrieve controller and pass TCIS again
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.setTCIS(tcis);  // Ensure continuity

            Stage stage = (Stage) collectionListView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
