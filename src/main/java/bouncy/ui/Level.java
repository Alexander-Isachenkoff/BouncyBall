package bouncy.ui;

import bouncy.model.GameObject;
import bouncy.model.LevelData;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import lombok.Getter;

public class Level extends Pane {

    @Getter
    private final LevelData levelData = new LevelData();
    private String levelPath;

    public Level() {
        this.levelData.setRemoveListener(this::removeNodeByGameObject);
    }

    public Level(String levelPath) {
        this();
        this.levelPath = levelPath;
        reload();
    }

    public void load(String levelPath) {
        this.levelPath = levelPath;
        reload();
    }

    public void reload() {
        this.initLevelData(LevelData.load(levelPath));
    }

    public void clear() {
        getLevelData().clear();
    }

    private void initLevelData(LevelData levelData) {
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

    public GameObjectNode getNode(GameObject gameObject) {
        return getChildren().stream()
                .filter(node -> node instanceof GameObjectNode)
                .map(node -> (GameObjectNode) node)
                .filter(node -> node.getGameObject() == gameObject)
                .findFirst()
                .get();
    }

}
