package bouncy.controller;

import bouncy.ui.CustomLevels;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class CustomLevelsTest extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new CustomLevels()));
        stage.show();
    }

    @Test
    void test() {
        launch();
    }

}