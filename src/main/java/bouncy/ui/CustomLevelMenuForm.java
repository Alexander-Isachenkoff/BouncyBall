package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

class CustomLevelMenuForm extends FxmlForm {

    private final CustomLevel level;

    CustomLevelMenuForm(String fxml, CustomLevel level) {
        super(fxml);
        this.level = level;
        getScene().setFill(Color.TRANSPARENT);
        getStage().initStyle(StageStyle.TRANSPARENT);
        getStage().initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    private void onMenu() {
        getStage().close();
        Main.toCustomLevels();
    }

    @FXML
    private void onRetry() {
        getStage().close();
        level.restart();
    }

}
