package bouncy.ui;

import javafx.scene.layout.FlowPane;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LevelsFlowPane extends FlowPane {

    public LevelsFlowPane() {
        this.getStyleClass().add("levels-flow-pane");
    }

    public void loadLevels(List<String> fileNames, Function<String, LevelCell> levelCellFactory) {
        getChildren().clear();
        List<LevelCell> collect = fileNames.parallelStream()
                .map(levelCellFactory)
                .collect(Collectors.toList());
        getChildren().setAll(collect);
    }

}
