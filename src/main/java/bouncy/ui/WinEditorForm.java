package bouncy.ui;

import bouncy.Main;
import bouncy.model.LevelData;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class WinEditorForm extends FxmlForm {

    private final PlayingLevel level;

    public WinEditorForm(PlayingLevel level) {
        super("fxml/win_editor.fxml");
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
        Main.toLevelEditor();
    }

    @FXML
    private void onSave() {
        this.getStage().close();
        InputNameForm form = new InputNameForm();
        form.setOnSave(s -> {
            LevelData levelData = LevelData.loadTemp();
            levelData.setName(s);
            levelData.save();
            Main.toLevelEditor();
        });
        form.show();
    }

}
