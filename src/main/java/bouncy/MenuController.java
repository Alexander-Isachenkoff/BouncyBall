package bouncy;

import bouncy.view.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class MenuController {

    public VBox root;

    @FXML
    private void onPlay() {
        Parent parent = ViewUtils.loadView("fxml/levels.fxml");
        root.getScene().setRoot(parent);
    }

    @FXML
    private void onLevelEditor() {
        Parent parent = ViewUtils.loadView("fxml/level_editor.fxml");
        root.getScene().setRoot(parent);
    }

    @FXML
    private void onExit() {
        root.getScene().getWindow().hide();
    }

}
