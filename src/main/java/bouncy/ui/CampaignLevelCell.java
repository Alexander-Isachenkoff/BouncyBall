package bouncy.ui;

import bouncy.model.LevelProgressData;
import bouncy.model.LevelsProgressData;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CampaignLevelCell extends LevelCell {

    public CampaignLevelCell(String levelPath) {
        super(levelPath);

        LevelsProgressData levelsProgressData = LevelsProgressData.load();
        LevelProgressData progressData = levelsProgressData.get(levelPath);
        int index = progressData.getIndex();
        boolean isAvailable = (index == 1) || levelsProgressData.get(index - 1).isDone();
        this.setDisable(!isAvailable);

        if (progressData.isDone()) {
            Label label = new Label("Done");
            label.getStyleClass().add("done-label");
            getChildren().add(label);

            AnchorPane.setTopAnchor(label, 0.0);
            AnchorPane.setRightAnchor(label, 0.0);
            AnchorPane.setLeftAnchor(label, 0.0);
            AnchorPane.setBottomAnchor(label, 0.0);
        }
    }

    @Override
    public PlayingLevel createLevel(String levelPath) {
        return new CampaignLevel(levelPath);
    }

}
