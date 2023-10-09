package bouncy;

import bouncy.model.GameObject;
import bouncy.model.LevelData;
import bouncy.model.Player;
import bouncy.model.Star;
import bouncy.view.GameObjectNode;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.HashSet;
import java.util.Set;
import java.util.TimerTask;

public class Level extends Pane {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final Label scoreLabel = new Label();
    private final Label fpsLabel = new Label();
    private final LevelData levelData = new LevelData();
    private long lastUpdate;
    private double dtSeconds;
    private int score = 0;

    public Level() {
        getChildren().add(new HBox(5, scoreLabel, fpsLabel));
        setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });
        setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });
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
                .ifPresent(node -> {
                    Platform.runLater(() -> getChildren().remove(node));
                });
    }

    public void start() {
        requestFocus();
        lastUpdate = System.currentTimeMillis();

        int delay = Math.round(1000 / 120f);

        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 0, delay);
    }

    private void update() {
        requestFocus();
        long dtMills = System.currentTimeMillis() - lastUpdate;
        dtSeconds = dtMills / 1000.0;
        Platform.runLater(() -> fpsLabel.setText("FPS: " + Math.round(1 / dtSeconds)));
        lastUpdate = System.currentTimeMillis();
        processBall();
    }

    private void processBall() {
        Player player = levelData.getPlayer();

        player.move(dtSeconds);

        if (keysPressed.contains(KeyCode.LEFT)) {
            player.moveLeft(dtSeconds);
        }

        if (keysPressed.contains(KeyCode.RIGHT)) {
            player.moveRight(dtSeconds);
        }

        levelData.getObjects(Star.class).stream()
                .filter(player::intersects)
                .forEach(star -> {
                    remove(star);
                    score++;
                });

        Platform.runLater(() -> scoreLabel.setText("Score: " + score));
    }

}
