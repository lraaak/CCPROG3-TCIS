import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import com.*;

public class Main extends Application {

    public static TCIS tcis;

    @Override
    public void start(Stage primaryStage) throws Exception {


        tcis = new TCIS();

        Collection.addCard("Shadow Reaper", "RARE", "FULL-ART", 22);
        Collection.addCard("Lunar Guardian", "COMMON", "NORMAL", 8);
        Collection.addCard("Crystal Viper", "LEGENDARY", "ALT-ART", 30);
        Collection.addCard("Frostbite Serpent", "UNCOMMON", "NORMAL", 11);
        Collection.addCard("Thunder Stag", "RARE", "EXTENDED-ART", 19);
        Collection.addCard("Molten Phantom", "LEGENDARY", "FULL-ART", 28);
        Collection.addCard("Wind Whisperer", "COMMON", "NORMAL", 6);
        Collection.addCard("Obsidian Raven", "UNCOMMON", "NORMAL", 10);
        Collection.addCard("Solar Griffin", "RARE", "FULL-ART", 21);
        Collection.addCard("Twilight Golem", "LEGENDARY", "ALT-ART", 35);

        Decks deckA = new SellableDeck("Deck A");

        tcis.addDeck(deckA);




        if (tcis.isValid()) {
            System.out.println("TCIS is properly initialized.");
        } else {
            System.out.println("TCIS initialization failed.");

            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController controller = loader.getController();
        controller.setTCIS(tcis);


        // Set the scene with the loaded FXML
        primaryStage.setTitle("Trading Card Inventory System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
