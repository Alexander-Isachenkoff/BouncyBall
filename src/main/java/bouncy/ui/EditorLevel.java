package bouncy.ui;

import bouncy.model.LevelData;

import java.util.function.Supplier;

public class EditorLevel extends PlayingLevel {

    public EditorLevel(Supplier<LevelData> levelDataLoader) {
        super(levelDataLoader);
    }

    @Override
    protected void onDead() {

    }

    @Override
    protected void onWin() {

    }

}
