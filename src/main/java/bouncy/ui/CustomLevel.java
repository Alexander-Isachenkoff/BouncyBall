package bouncy.ui;

import javafx.application.Platform;

public class CustomLevel extends PlayingLevel {

    public CustomLevel(String levelPath) {
        super(levelPath);
    }

    @Override
    protected void onDead() {
        Platform.runLater(() -> new CustomLevelMenuForm("fxml/defeat_custom.fxml", this).show());
    }

    @Override
    protected void onWin() {
        Platform.runLater(() -> new CustomLevelMenuForm("fxml/win_custom.fxml", this).show());
    }

}
