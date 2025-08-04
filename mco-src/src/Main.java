import com.Collection;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import com.*;
import java.util.*;

import java.nio.file.Paths;
import java.util.Objects;

public class Main extends Application {

    public static TCIS tcis;

    MediaPlayer mediaPlayer;
    public void music(){
        String s = "TCIS_MUSIC.mp3";
        Media h =  new Media(Paths.get(s).toUri().toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(0.3);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        tcis = new TCIS();
        music();


        ArrayList<Cards> generatedCards = new ArrayList<>();

        generatedCards.add(new Cards("Shadow Reaper", "RARE", "FULL-ART", 22));
        generatedCards.add(new Cards("Lunar Guardian", "COMMON", "NORMAL", 8));
        generatedCards.add(new Cards("Crystal Viper", "LEGENDARY", "ALT-ART", 30));
        generatedCards.add(new Cards("Frostbite Serpent", "UNCOMMON", "NORMAL", 11));
        generatedCards.add(new Cards("Thunder Stag", "RARE", "EXTENDED-ART", 19));
        generatedCards.add(new Cards("Molten Phantom", "LEGENDARY", "FULL-ART", 28));
        generatedCards.add(new Cards("Wind Whisperer", "COMMON", "NORMAL", 6));
        generatedCards.add(new Cards("Obsidian Raven", "UNCOMMON", "NORMAL", 10));
        generatedCards.add(new Cards("Solar Griffin", "RARE", "FULL-ART", 21));
        generatedCards.add(new Cards("Twilight Golem", "LEGENDARY", "ALT-ART", 35));
        generatedCards.add(new Cards("Aether Sprite", "UNCOMMON", "NORMAL", 12));
        generatedCards.add(new Cards("Blazing Lynx", "RARE", "EXTENDED-ART", 18));
        generatedCards.add(new Cards("Phantom Drake", "LEGENDARY", "FULL-ART", 27));
        generatedCards.add(new Cards("Celestial Wurm", "COMMON", "NORMAL", 7));
        generatedCards.add(new Cards("Duskwatch Panther", "UNCOMMON", "NORMAL", 9));
        generatedCards.add(new Cards("Verdant Hydra", "RARE", "FULL-ART", 23));
        generatedCards.add(new Cards("Glacial Chimera", "LEGENDARY", "ALT-ART", 29));
        generatedCards.add(new Cards("Stormcaller Imp", "COMMON", "NORMAL", 5));
        generatedCards.add(new Cards("Voidcaller Witch", "RARE", "EXTENDED-ART", 20));
        generatedCards.add(new Cards("Ironhide Brute", "UNCOMMON", "NORMAL", 13));
        generatedCards.add(new Cards("Nightveil Banshee", "LEGENDARY", "FULL-ART", 31));
        generatedCards.add(new Cards("Sunflare Eagle", "RARE", "EXTENDED-ART", 24));
        generatedCards.add(new Cards("Thornlash Widow", "UNCOMMON", "NORMAL", 14));
        generatedCards.add(new Cards("Galehorn Minotaur", "RARE", "FULL-ART", 26));
        generatedCards.add(new Cards("Brightscale Drake", "COMMON", "NORMAL", 9));
        generatedCards.add(new Cards("Ashen Stalker", "LEGENDARY", "ALT-ART", 33));
        generatedCards.add(new Cards("Meadow Sentry", "UNCOMMON", "NORMAL", 10));
        generatedCards.add(new Cards("Bloodfang Warg", "RARE", "EXTENDED-ART", 19));
        generatedCards.add(new Cards("Mirror Fiend", "LEGENDARY", "FULL-ART", 36));
        generatedCards.add(new Cards("Ivory Golem", "COMMON", "NORMAL", 8));

        Collection.addCard("Infernal Wyrm", "RARE", "EXTENDED-ART", 22);
        Collection.addCard("Silverfang Sentinel", "RARE", "FULL-ART", 14);
        Collection.addCard("Blightshade Oracle", "UNCOMMON", "ALT-ART", 10);
        Collection.addCard("Twilight Phoenix", "LEGENDARY", "FULL-ART", 25);
        Collection.addCard("Verdant Hermit", "RARE", "EXTENDED-ART", 13);


// Create and fill deck with first 10 cards
        Decks deckA = new SellableDeck("Deck A");
        for (int i = 0; i < 10; i++) {
            deckA.addCard(generatedCards.get(i));
        }
        tcis.addDeck(deckA);

// Create and fill binder with next 20 cards
        Binders binderA = new NonCuratedBinder("Binder A");
        for (int i = 10; i < 30; i++) {
            binderA.addCard(generatedCards.get(i));
        }
        tcis.addBinder(binderA);

        Binders binderB = new RaresBinder("Rares");
        Binders binderC = new LuxuryBinder("Luxury");

        tcis.addBinder(binderB);
        tcis.addBinder(binderC);
// Verify TCIS state
        if (tcis.isValid()) {
            System.out.println("TCIS initialized with card array, deck, and binder.");
        } else {
            System.out.println("TCIS initialization failed.");
        }


// Validate TCIS setup
        if (tcis.isValid()) {
            System.out.println("TCIS initialized with 1 deck and 1 binder.");
        } else {
            System.out.println("TCIS initialization failed.");
        }


        // Load the FXML file for the MainMenu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/MainMenu.fxml"));
        Parent root = loader.load();

        MainMenuController controller = loader.getController();
        controller.setTCIS(tcis);

        // Set the scene with the loaded FXML
        primaryStage.setTitle("Trading Card Inventory System");
        primaryStage.setScene(new Scene(root));

        // Set the application icon
        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/resources/TCIS_COVER.png")));
        primaryStage.getIcons().add(appIcon);

        // Show the window
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
