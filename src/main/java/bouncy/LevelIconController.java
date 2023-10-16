package bouncy;

import bouncy.model.LevelData;
import bouncy.model.LevelProgressData;
import bouncy.ui.PlayingLevel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

public class LevelIconController {

    @FXML
    private AnchorPane root;
    @FXML
    private Pane checkMarkPlace;
    @FXML
    private Button button;
    @FXML
    private Polyline checkMark;

    public void init(LevelProgressData levelProgressData, boolean isAvailable) {
        button.setText(String.valueOf(levelProgressData.getIndex()));
        checkMark.setVisible(levelProgressData.isDone());
        button.setOnAction(event -> {
            PlayingLevel playingLevel = new PlayingLevel(LevelData.load(levelProgressData.getFileName()));
            checkMark.getScene().setRoot(playingLevel);
            playingLevel.start();
        });
        root.setDisable(!isAvailable);
        checkMarkPlace.setVisible(isAvailable);
    }

}
