package bouncy.ui;

import bouncy.Main;
import bouncy.controller.LevelEditor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.io.IOException;

public class ViewUtils {

    public static Parent loadMenu() {
        return loadView("fxml/menu.fxml");
    }

    public static Parent loadLevels() {
        return loadView("fxml/levels.fxml");
    }

    public static Pair<LevelEditor, Parent> loadLevelEditor() {
        return loadWithController("fxml/level_editor.fxml");
    }

    private static Parent loadView(String fxml) {
        return loadWithController(fxml).getValue();
    }

    public static <T> Pair<T, Parent> loadWithController(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml));
        Parent load;
        try {
            load = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(fxmlLoader.getController(), load);
    }

}
