package bouncy.controller;

import bouncy.model.LevelData;
import bouncy.ui.UserLevel;
import bouncy.ui.ViewUtils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Pair;

public class LevelListCell extends HBox {

    private final String levelPath;
    private final Label levelNameLabel = new Label();

    public LevelListCell(String levelPath) {
        this.levelPath = levelPath;
        levelNameLabel.setMaxWidth(Integer.MAX_VALUE);

        Button playButton = new MiniButton("Play", this::onPlay);
        Button editButton = new MiniButton("Edit", this::onEdit);
        Button deleteButton = new MiniButton("Delete", this::onDelete);
        HBox buttonsBox = new HBox(10, playButton, editButton, deleteButton);
        buttonsBox.setPadding(new Insets(0, 0, 5, 0));

        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().add("level-cell");
        getChildren().addAll(levelNameLabel, buttonsBox);

        HBox.setHgrow(levelNameLabel, Priority.ALWAYS);

        initLevelData(LevelData.load(levelPath));
    }

    public void initLevelData(LevelData levelData) {
        levelNameLabel.setText(levelData.getName());
    }

    @FXML
    private void onEdit() {
        Pair<LevelEditor, Parent> load = ViewUtils.loadLevelEditor();
        LevelEditor levelEditor = load.getKey();
        levelEditor.loadLevelData(levelPath);
        getScene().setRoot(load.getValue());
    }

    @FXML
    private void onDelete() {

    }

    @FXML
    private void onPlay() {
        UserLevel level = new UserLevel(() -> LevelData.load(levelPath));
        getScene().setRoot(level);
        level.start();
    }

    private static class MiniButton extends Button {
        public MiniButton(String text, Runnable onAction) {
            super(text);
            this.getStyleClass().add("mini-button");
            this.setOnAction(event -> onAction.run());
        }
    }

}
