package bouncy.ui;

import bouncy.model.*;
import bouncy.util.Sets;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class PlayingLevel extends Level {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final Set<KeyCode> leftKeys = Sets.of(KeyCode.A, KeyCode.LEFT);
    private final Set<KeyCode> rightKeys = Sets.of(KeyCode.D, KeyCode.RIGHT);
    private long lastUpdate;
    private double dtSeconds;
    private double totalSeconds;
    private AnimationTimer timer;

    public PlayingLevel(String levelPath) {
        super(levelPath);
        setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });
        setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });
    }

    private static <T> boolean containsAny(Collection<T> collection1, Collection<T> collection2) {
        return collection2.stream().anyMatch(collection1::contains);
    }

    public void start() {
        requestFocus();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };
        timer.start();
    }

    void restart() {
        stop();
        keysPressed.clear();
        reload();
        start();
    }

    void stop() {
        timer.stop();
    }

    private void update(long now) {
        Platform.runLater(this::requestFocus);
        if (lastUpdate == 0) {
            lastUpdate = now;
        }
        long dt = now - lastUpdate;
        dtSeconds = dt / 1e9;
        System.out.println(dt / 1e6);
        totalSeconds += dtSeconds;
        lastUpdate = now;
        processBall();

        for (MovingGameObject object : getLevelData().getObjects(MovingGameObject.class)) {
            object.move(dtSeconds);
        }

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
            onWin();
            stop();
        }

        if (player.isDead()) {
            onDead();
            stop();
        }
    }

    protected abstract void onDead();

    protected abstract void onWin();

    private boolean isWin() {
        return getLevelData().getObjects(Star.class).isEmpty();
    }

}
