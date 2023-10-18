package bouncy.controller;

import bouncy.Main;
import bouncy.ui.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class MenuController {

    public VBox root;

    @FXML
    private void onPlay() {
        Parent parent = ViewUtils.loadLevels();
        root.getScene().setRoot(parent);
    }

    @FXML
    private void onLevelEditor() {
        Main.toTempLevelEditor();
    }

    @FXML
    private void onExit() {
        root.getScene().getWindow().hide();
    }

    @FXML
    private void onUserLevels() {
        Parent parent = ViewUtils.loadUserLevels();
        root.getScene().setRoot(parent);
    }

}
