package bouncy.ui;

import bouncy.model.GameObject;
import bouncy.model.LevelData;
import bouncy.model.Player;
import bouncy.view.GameObjectNode;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.HashSet;

public class Level extends Pane {

    private final LevelData levelData = new LevelData();

    public void clear() {
        new HashSet<>(levelData.getGameObjects()).forEach(this::remove);
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
