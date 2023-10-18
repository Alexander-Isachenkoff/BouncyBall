package bouncy.controller;

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
        Parent parent = ViewUtils.loadLevelEditor();
        root.getScene().setRoot(parent);
    }

    @FXML
    private void onExit() {
        root.getScene().getWindow().hide();
    }

    @FXML
    private void onUserLevels() {
        Parent parent = ViewUtils.loadView("fxml/user_levels.fxml");
        root.getScene().setRoot(parent);
    }

}
