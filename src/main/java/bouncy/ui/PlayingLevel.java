package bouncy.ui;

import bouncy.model.LevelData;
import bouncy.model.Player;
import bouncy.model.Spikes;
import bouncy.model.Star;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingLevel extends Level {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final Label scoreLabel = new Label();
    private final Label fpsLabel = new Label();
    private final Timer timer = new Timer();
    private long lastUpdate;
    private double dtSeconds;
    private int score = 0;

    public PlayingLevel(LevelData levelData) {
        super(levelData);
        getChildren().add(new HBox(5, scoreLabel, fpsLabel));
        setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });
        setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });
    }

    public void start() {
        requestFocus();
        lastUpdate = System.currentTimeMillis();

        int delay = Math.round(1000 / 120f);

        timer.schedule(new TimerTask() {
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
        Player player = getLevelData().getPlayer();

        player.move(dtSeconds);

        if (keysPressed.contains(KeyCode.LEFT)) {
            player.moveLeft(dtSeconds);
        }

        if (keysPressed.contains(KeyCode.RIGHT)) {
            player.moveRight(dtSeconds);
        }

        getLevelData().getObjects(Star.class).stream()
                .filter(player::intersects)
                .forEach(star -> {
                    remove(star);
                    score++;
                });

        boolean spikesCollides = getLevelData().getObjects(Spikes.class)
                .stream()
                .anyMatch(player::intersects);

        if (spikesCollides) {
            timer.cancel();
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.NONE, "Проигрыш", ButtonType.OK).show();
            });
        }

        Platform.runLater(() -> scoreLabel.setText("Score: " + score));
    }

}
