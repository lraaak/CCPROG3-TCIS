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

    public static void main(String[] args) {
       TCIS tcis = new TCIS();
               tcis.displayMenu();
    }
}