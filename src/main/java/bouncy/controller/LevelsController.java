package bouncy.controller;

import bouncy.model.LevelProgressData;
import bouncy.model.LevelsProgressData;
import bouncy.ui.CampaignLevelCell;
import bouncy.ui.LevelsFlowPane;
import bouncy.ui.ViewUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LevelsController {

    private final LevelsFlowPane levelsPane = new LevelsFlowPane();
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        scrollPane.setContent(levelsPane);
        reload();
    }

    private void reload() {
        LevelsProgressData levelsProgressData = LevelsProgressData.load();
        List<String> fileNames = levelsProgressData.getLevelProgressData().stream()
                .sorted(Comparator.comparingInt(LevelProgressData::getIndex))
                .map(LevelProgressData::getFileName)
                .collect(Collectors.toList());
        levelsPane.loadLevels(fileNames, CampaignLevelCell::new);
    }

    @FXML
    private void onReset() {
        LevelsProgressData progressData = LevelsProgressData.load();
        for (LevelProgressData data : progressData.getLevelProgressData()) {
            data.setDone(false);
        }
        progressData.save();
        reload();
    }

    @FXML
    private void onMenu() {
        levelsPane.getScene().setRoot(ViewUtils.loadMenu());
    }

}
