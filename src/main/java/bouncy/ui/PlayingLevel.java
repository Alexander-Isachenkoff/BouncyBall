package bouncy.ui;

import bouncy.model.*;
import bouncy.util.Sets;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.function.Supplier;

public abstract class PlayingLevel extends Level {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final Label fpsLabel = new Label();
    private final int delay = Math.round(1000 / 120f);
    private final Supplier<LevelData> levelDataLoader;
    private Timer timer;
    private long lastUpdate;
    private double dtSeconds;
    private double totalSeconds;
    private final Set<KeyCode> leftKeys = Sets.of(KeyCode.A, KeyCode.LEFT);
    private final Set<KeyCode> rightKeys = Sets.of(KeyCode.D, KeyCode.RIGHT);

    public PlayingLevel(Supplier<LevelData> levelDataLoader) {
        super(levelDataLoader.get());
        this.levelDataLoader = levelDataLoader;
        Button button = new Button("restart");
        button.setOnAction(event -> restart());
        getChildren().add(new HBox(5, fpsLabel, button));
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
        timer = new Timer();
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
        totalSeconds += dtSeconds;
        Platform.runLater(() -> fpsLabel.setText("FPS: " + Math.round(1 / dtSeconds)));
        lastUpdate = System.currentTimeMillis();
        processBall();

        getLevelData().getObjects(Liquid.class)
                .forEach(liquid -> liquid.setStateTime(totalSeconds));
    }

    private void processBall() {
        Player player = getLevelData().getPlayer();

        player.move(dtSeconds);

        for (GameObject gameObject : new HashSet<>(getLevelData().getGameObjects())) {
            gameObject.affectPlayer(player);
        }

        if (containsAny(keysPressed, leftKeys)) {
            player.moveLeft(dtSeconds);
        }

        if (containsAny(keysPressed, rightKeys)) {
            player.moveRight(dtSeconds);
        }

        if (isWin()) {
            timer.cancel();
            onWin();
        }

        if (player.isDead()) {
            timer.cancel();
            onDead();
        }
    }

    private static <T> boolean containsAny(Collection<T> collection1, Collection<T> collection2) {
        return collection2.stream().anyMatch(collection1::contains);
    }

    protected abstract void onDead();

    protected abstract void onWin();

    private boolean isWin() {
        return getLevelData().getObjects(Star.class).isEmpty();
    }

    void restart() {
        keysPressed.clear();
        initLevelData(levelDataLoader.get());
        start();
    }

}
