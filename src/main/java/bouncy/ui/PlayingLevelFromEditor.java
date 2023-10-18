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
            DefeatEditorForm form = new DefeatEditorForm(this);
            form.show();
        });
    }

    @Override
    protected void onWin() {
        Platform.runLater(() -> {
            WinEditorForm form = new WinEditorForm(this);
            form.show();
        });
    }

}
