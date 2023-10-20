package bouncy.ui;

import bouncy.controller.LevelEditor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.io.File;

public class CustomLevelCell extends LevelCell {

    public CustomLevelCell(String levelPath) {
        super(levelPath);

        Button editButton = new MiniButton("Edit", this::onEdit);
        Button deleteButton = new MiniButton("Delete", this::onDelete);
        HBox buttonsBox = new HBox(10, editButton, deleteButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(0, 0, 10, 0));
        getChildren().add(buttonsBox);
        AnchorPane.setLeftAnchor(buttonsBox, 0.0);
        AnchorPane.setRightAnchor(buttonsBox, 0.0);
        AnchorPane.setBottomAnchor(buttonsBox, 0.0);
    }

    @Override
    public PlayingLevel createLevel(String levelPath) {
        return new CustomLevel(levelPath);
    }

    private void onEdit() {
        Pair<LevelEditor, Parent> load = ViewUtils.loadLevelEditor();
        LevelEditor levelEditor = load.getKey();
        levelEditor.loadLevelData(getLevelPath());
        getScene().setRoot(load.getValue());
    }

    private void onDelete() {
        if (new File(getLevelPath()).delete()) {
            ((Pane) getParent()).getChildren().remove(this);
        }
    }

}
