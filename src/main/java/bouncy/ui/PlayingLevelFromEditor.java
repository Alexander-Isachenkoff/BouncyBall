package bouncy.ui;

import javafx.application.Platform;

public class PlayingLevelFromEditor extends PlayingLevel {

    public PlayingLevelFromEditor(String levelPath) {
        super(levelPath);
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
