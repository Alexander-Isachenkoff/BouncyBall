package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class DefeatEditorForm extends FxmlForm {

    private final PlayingLevel level;

    public DefeatEditorForm(PlayingLevel level) {
        super("fxml/defeat_editor.fxml");
        this.level = level;
        getScene().setFill(Color.TRANSPARENT);
        this.getStage().initStyle(StageStyle.TRANSPARENT);
        this.getStage().initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    private void onRetry() {
        this.getStage().close();
        level.restart();
    }

    @FXML
    private void onEditor() {
        this.getStage().close();
        Main.toTempLevelEditor();
    }

}
