package bouncy.controller;

import bouncy.Main;
import bouncy.ui.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class DefeatController {

    @FXML
    private VBox root;

    @Setter
    private Runnable onRetry;

    @FXML
    private void onRetry() {
        onRetry.run();
    }

    @FXML
    private void onMenu() {
        root.getScene().getWindow().hide();
        Main.getStage().getScene().setRoot(ViewUtils.loadMenu());
    }

}
