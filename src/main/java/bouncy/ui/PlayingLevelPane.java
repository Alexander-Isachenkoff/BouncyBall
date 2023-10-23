package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import lombok.Setter;

public class PlayingLevelPane extends FxmlBox {

    private final PlayingLevel level;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button backButton;
    @Setter
    private Runnable onBack;

    public PlayingLevelPane(PlayingLevel level) {
        super("fxml/playing_level_pane.fxml");
        this.level = level;

        anchorPane.getChildren().add(0, level);
        AnchorPane.setTopAnchor(level, 0.0);
        AnchorPane.setBottomAnchor(level, 0.0);
        AnchorPane.setLeftAnchor(level, 0.0);
        AnchorPane.setRightAnchor(level, 0.0);

        backButton.setVisible(false);
        backButton.setManaged(false);
    }

    public PlayingLevelPane(PlayingLevel level, Runnable onBack) {
        this(level);
        setOnBack(onBack);
        if (onBack != null) {
            backButton.setVisible(true);
            backButton.setManaged(true);
        }
    }

    @FXML
    private void onRestart() {
        level.restart();
    }

    @FXML void onBack() {
        onBack.run();
    }

    @FXML
    private void onMenu() {
        level.stop();
        Main.toMainMenu();
    }

}
