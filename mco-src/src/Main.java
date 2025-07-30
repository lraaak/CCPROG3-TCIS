import com.*;

import com.Collection;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        TCIS tcis = new TCIS();
        tcis.displayMenu();
    }
}
