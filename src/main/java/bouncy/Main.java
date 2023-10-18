package bouncy;

import bouncy.ui.ViewUtils;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        launch();
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

    public static Stage getStage() {
        return stage;
    }

    public static void toLevelEditor() {
        stage.getScene().setRoot(ViewUtils.loadLevelEditor());
    }
}
