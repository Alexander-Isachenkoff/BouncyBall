package bouncy.controller;

import bouncy.model.LevelProgressData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;
import lombok.Setter;

public class LevelIconController {

    @FXML
    private AnchorPane root;
    @FXML
    private Pane checkMarkPlace;
    @FXML
    private Button button;
    @FXML
    private Polyline checkMark;

    @Setter
    private Runnable onClick = () -> {
    };

    public void init(LevelProgressData levelProgressData, boolean isAvailable) {
        button.setText(String.valueOf(levelProgressData.getIndex()));
        checkMark.setVisible(levelProgressData.isDone());
        button.setOnAction(event -> onClick.run());
        root.setDisable(!isAvailable);
        checkMarkPlace.setVisible(isAvailable);
    }

}
