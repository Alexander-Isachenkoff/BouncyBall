package bouncy.controller;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.Optional;

public class UserLevelsController {

    public VBox listBox;

    @FXML
    private void initialize() {
        File[] levelsFiles = Optional.ofNullable(new File("data/user_levels").listFiles()).orElse(new File[0]);
        for (File file : levelsFiles) {
            LevelListCell cell = new LevelListCell(file.getAbsolutePath());
            listBox.getChildren().add(cell);
        }
    }

    @FXML
    private void onMenu() {
        Main.toMainMenu();
    }

}
