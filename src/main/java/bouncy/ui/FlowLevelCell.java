package bouncy.ui;

import bouncy.controller.LevelEditor;
import bouncy.model.LevelData;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class FlowLevelCell extends AnchorPane {

    private final String levelPath;
    private final Label levelNameLabel = new Label();

    public FlowLevelCell(String levelPath) {
        this.levelPath = levelPath;
        LevelData levelData = LevelData.load(levelPath);

        Button playButton = new BigButton("Play", this::onPlay);
        Button editButton = new MiniButton("Edit", this::onEdit);
        Button deleteButton = new MiniButton("Delete", this::onDelete);
        HBox buttonsBox = new HBox(10, editButton, deleteButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 10, 0));

        VBox levelImage = levelImage(levelData);
        this.getStyleClass().add("level-cell");

        VBox vBox = new VBox(10, levelNameLabel, playButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        getChildren().addAll(levelImage, vBox, buttonsBox);

        AnchorPane.setLeftAnchor(buttonsBox, 0.0);
        AnchorPane.setRightAnchor(buttonsBox, 0.0);
        AnchorPane.setBottomAnchor(buttonsBox, 0.0);

        AnchorPane.setTopAnchor(vBox, 0.0);
        AnchorPane.setLeftAnchor(vBox, 0.0);
        AnchorPane.setRightAnchor(vBox, 0.0);

        initLevelData(levelData);
    }

    private static VBox levelImage(LevelData levelData) {
        Level level = new Level();
        VBox vBox = new VBox(new Group(level));
        vBox.setPrefSize(160, 140);
        vBox.setAlignment(Pos.CENTER);
        level.setScaleX(0.2);
        level.setScaleY(0.2);
        level.initLevelData(levelData);
        vBox.getStyleClass().add("level-image");
        return vBox;
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

    private static class BigButton extends Button {
        public BigButton(String text, Runnable onAction) {
            super(text);
            this.getStyleClass().add("big-button");
            this.setOnAction(event -> onAction.run());
        }
    }

    private static class MiniButton extends Button {
        public MiniButton(String text, Runnable onAction) {
            super(text);
            this.getStyleClass().add("mini-button");
            this.setOnAction(event -> onAction.run());
        }
    }

}
