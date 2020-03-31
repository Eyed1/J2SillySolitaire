import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

/**
 * Class to run to play the game: make sure you run on the JavaFX thread
 */
public class SillySolitaire extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        // Integrate into the OS menus: not sure I like this
        final String os = System.getProperty ("os.name");
        if (os != null && os.startsWith ("Mac")) {
            MenuBar menuBar = (MenuBar) root.lookup("#mainMenu");
            if (menuBar != null) {
                menuBar.setUseSystemMenuBar(true);    
            }
        }        
        Scene scene = new Scene(root);

        stage.setMinWidth(300);
        stage.setMinHeight(300);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
