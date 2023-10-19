package bouncy.controller;

import bouncy.ui.UserLevels;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class UserLevelsTest extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new UserLevels()));
        stage.show();
    }

    @Test
    void test() {
        launch();
    }

}