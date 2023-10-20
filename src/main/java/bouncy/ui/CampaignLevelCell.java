package bouncy.ui;

import bouncy.model.LevelProgressData;
import bouncy.model.LevelsProgressData;

public class CampaignLevelCell extends LevelCell {

    public CampaignLevelCell(String levelPath) {
        super(levelPath);

        LevelsProgressData levelsProgressData = LevelsProgressData.load();
        LevelProgressData progressData = levelsProgressData.get(levelPath);
        int index = progressData.getIndex();
        boolean isAvailable = (index == 1) || levelsProgressData.get(index - 1).isDone();
        this.setDisable(!isAvailable);
    }

    @Override
    public PlayingLevel createLevel(String levelPath) {
        return new CampaignLevel(levelPath);
    }

}
