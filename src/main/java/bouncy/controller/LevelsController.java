package bouncy.controller;

import bouncy.model.LevelProgressData;
import bouncy.model.LevelsProgressData;
import bouncy.ui.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;

import java.util.Comparator;
import java.util.List;

public class LevelsController {

    @FXML
    private FlowPane levelsPane;

    @FXML
    private void initialize() {
        levelsPane.getChildren().clear();

        List<LevelProgressData> levelProgressDataList = LevelsProgressData.load();
        levelProgressDataList.sort(Comparator.comparingInt(LevelProgressData::getIndex));
        for (int i = 0; i < levelProgressDataList.size(); i++) {
            boolean isAvailable = (i == 0) || levelProgressDataList.get(i - 1).isDone();
            Node button = createLevelButton(levelProgressDataList.get(i), isAvailable);
            levelsPane.getChildren().add(button);
        }
    }

    private Node createLevelButton(LevelProgressData levelProgressData, boolean isAvailable) {
        Pair<?, Parent> load = ViewUtils.load("fxml/level_icon.fxml");
        LevelIconController controller = (LevelIconController) load.getKey();
        controller.init(levelProgressData, isAvailable);
        return load.getValue();
    }

    @FXML
    private void onMenu() {
        levelsPane.getScene().setRoot(ViewUtils.loadMenu());
    }

}
