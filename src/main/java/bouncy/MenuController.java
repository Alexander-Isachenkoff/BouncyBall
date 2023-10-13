package bouncy;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuController {

    public VBox root;

    @FXML
    private void onPlay() {

    }

    @FXML
    private void onLevelEditor() {
        Parent parent = loadView("fxml/level_editor.fxml");
        root.getScene().setRoot(parent);
    }

    @FXML
    private void onExit() {
        root.getScene().getWindow().hide();
    }

    private static Parent loadView(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Parent load;
        try {
            load = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return load;
    }

}
