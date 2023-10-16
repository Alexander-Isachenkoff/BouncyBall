package bouncy.ui;

import bouncy.model.GameObject;
import bouncy.model.LevelData;
import bouncy.model.Player;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@NoArgsConstructor
public class Level extends Pane {

    private final LevelData levelData = new LevelData();

    public Level(LevelData levelData) {
        this.initLevelData(levelData);
    }

    public void clear() {
        new HashSet<>(levelData.getGameObjects()).forEach(this::remove);
    }

    public void initLevelData(LevelData levelData) {
        this.clear();
        for (GameObject gameObject : levelData.getGameObjects()) {
            this.add(gameObject);
        }
        this.getLevelData().setName(levelData.getName());
    }

    public LevelData getLevelData() {
        return levelData;
    }

    public GameObjectNode add(GameObject gameObject) {
        if (gameObject instanceof Player) {
            ((Player) gameObject).setLevelData(levelData);
        }
        levelData.add(gameObject);
        GameObjectNode gameObjectNode = new GameObjectNode(gameObject);
        Platform.runLater(() -> getChildren().add(gameObjectNode));
        return gameObjectNode;
    }

    public void remove(GameObject gameObject) {
        levelData.remove(gameObject);
        getChildren().stream()
                .filter(node -> node instanceof GameObjectNode)
                .map(node -> (GameObjectNode) node)
                .filter(node -> node.getGameObject() == gameObject)
                .findFirst()
                .ifPresent(node -> Platform.runLater(() -> getChildren().remove(node)));
    }

}
