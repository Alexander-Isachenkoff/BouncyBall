package bouncy.ui;

import bouncy.model.LevelData;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;

public abstract class LevelCell extends AnchorPane {

    @Getter
    private final String levelPath;
    private final Label levelNameLabel = new Label();

    public LevelCell(String levelPath) {
        this.levelPath = levelPath;

        Button playButton = new LevelButton("Play ►", this::onPlay);

        VBox levelImage = levelImage();
        this.getStyleClass().add("level-cell");

        getChildren().addAll(levelImage, levelNameLabel, playButton);

        levelNameLabel.setAlignment(Pos.CENTER);
        levelNameLabel.setPadding(new Insets(5));
        AnchorPane.setLeftAnchor(levelNameLabel, 0.0);
        AnchorPane.setRightAnchor(levelNameLabel, 0.0);
        AnchorPane.setTopAnchor(levelNameLabel, 0.0);

        AnchorPane.setTopAnchor(playButton, 0.0);
        AnchorPane.setLeftAnchor(playButton, 0.0);
        AnchorPane.setRightAnchor(playButton, 0.0);
        AnchorPane.setBottomAnchor(playButton, 0.0);

        LevelData levelData = LevelData.load(levelPath);
        initLevelData(levelData);
    }

    private VBox levelImage() {
        Level level = new Level(levelPath);
        VBox vBox = new VBox(new Group(level));
        vBox.setPrefSize(160, 140);
        vBox.setAlignment(Pos.CENTER);
        level.setScaleX(0.2);
        level.setScaleY(0.2);
        vBox.getStyleClass().add("level-image");
        return vBox;
    }

    public void initLevelData(LevelData levelData) {
        levelNameLabel.setText(levelData.getName());
    }

    @FXML
    private void onPlay() {
        PlayingLevel level = createLevel(levelPath);
        getScene().setRoot(level);
        level.start();
    }

    public abstract PlayingLevel createLevel(String levelPath);

    private static class LevelButton extends Button {
        public LevelButton(String text, Runnable onAction) {
            super(text);
            this.getStyleClass().add("level-button");
            this.setOnAction(event -> onAction.run());
        }
    }

}
