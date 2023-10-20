package bouncy;

import bouncy.controller.LevelEditor;
import bouncy.ui.ViewUtils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void toTempLevelEditor() {
        Pair<LevelEditor, Parent> load = ViewUtils.loadLevelEditor();
        LevelEditor levelEditor = load.getKey();
        levelEditor.loadTempIfExists();
        stage.getScene().setRoot(load.getValue());
    }

    public static void toMainMenu() {
        stage.getScene().setRoot(ViewUtils.loadMenu());
    }

    @Override
    public void start(Stage stage) {
        Main.stage = stage;
        Parent parent = ViewUtils.loadMenu();
        stage.setScene(new Scene(parent));
        stage.setWidth(1024);
        stage.setHeight(600);
        stage.show();
    }

}
