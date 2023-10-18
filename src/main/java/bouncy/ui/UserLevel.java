package bouncy.ui;

import bouncy.model.LevelData;

import java.util.function.Supplier;

public class UserLevel extends PlayingLevel {

    public UserLevel(Supplier<LevelData> levelDataLoader) {
        super(levelDataLoader);
    }

    @Override
    protected void onDead() {

    }

    @Override
    protected void onWin() {

    }

}
