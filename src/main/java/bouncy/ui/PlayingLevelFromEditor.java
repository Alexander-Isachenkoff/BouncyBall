package bouncy.ui;

import bouncy.model.LevelData;
import javafx.application.Platform;

import java.util.function.Supplier;

public class PlayingLevelFromEditor extends PlayingLevel {

    public PlayingLevelFromEditor(Supplier<LevelData> levelDataLoader) {
        super(levelDataLoader);
    }

    @Override
    protected void onDead() {
        Platform.runLater(() -> {
            DefeatEditorForm defeatEditorForm = new DefeatEditorForm(this);
            defeatEditorForm.show();
        });
    }

    @Override
    protected void onWin() {

    }

}
