package bouncy.ui;

import bouncy.model.GameObject;
import bouncy.model.LevelData;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import lombok.Getter;

public class Level extends Pane {

    @Getter
    private final LevelData levelData = new LevelData();

    public Level() {
        this.levelData.setRemoveListener(this::removeNodeByGameObject);
    }

    public Level(LevelData levelData) {
        this();
        this.initLevelData(levelData);
    }

    public void clear() {
        getLevelData().clear();
    }

    public void initLevelData(LevelData levelData) {
        this.clear();
        for (GameObject gameObject : levelData.getGameObjects()) {
            this.add(gameObject);
        }
        this.getLevelData().setName(levelData.getName());
    }

    public GameObjectNode add(GameObject gameObject) {
        gameObject.setLevelData(getLevelData());
        getLevelData().add(gameObject);
        GameObjectNode gameObjectNode = new GameObjectNode(gameObject);
        Platform.runLater(() -> getChildren().add(gameObjectNode));
        return gameObjectNode;
    }

    public void remove(GameObject gameObject) {
        getLevelData().remove(gameObject);
    }

    private void removeNodeByGameObject(GameObject gameObject) {
        getChildren().stream()
                .filter(node -> node instanceof GameObjectNode)
                .map(node -> (GameObjectNode) node)
                .filter(node -> node.getGameObject() == gameObject)
                .findFirst()
                .ifPresent(node -> Platform.runLater(() -> getChildren().remove(node)));
    }

}
