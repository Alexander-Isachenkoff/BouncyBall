package bouncy.ui;

import bouncy.model.GameObject;
import bouncy.model.Liquid;
import bouncy.model.Player;
import bouncy.model.Star;
import bouncy.util.Sets;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class PlayingLevel extends Level {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final int delay = Math.round(1000 / 120f);
    private final Set<KeyCode> leftKeys = Sets.of(KeyCode.A, KeyCode.LEFT);
    private final Set<KeyCode> rightKeys = Sets.of(KeyCode.D, KeyCode.RIGHT);
    private Thread timer;
    private long lastUpdate;
    private double dtSeconds;
    private double totalSeconds;

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
        lastUpdate = System.currentTimeMillis();
        timer = new Thread(() -> {
            while (true) {
                update();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        timer.setDaemon(true);
        timer.start();
    }

    void restart() {
        stop();
        keysPressed.clear();
        reload();
        start();
    }

    public void stop() {
        timer.interrupt();
    }

    private void update() {
        Platform.runLater(this::requestFocus);
        long dtMills = System.currentTimeMillis() - lastUpdate;
        dtSeconds = dtMills / 1000.0;
        totalSeconds += dtSeconds;
        lastUpdate = System.currentTimeMillis();
        processBall();

        for (GameObject object : getLevelData().getGameObjects()) {
            if (object.getMotionPath() != null) {
                if (object.isForwardMotion()) {
                    double dx = object.getMotionPath().getEndX() - object.getX();
                    double dy = object.getMotionPath().getEndY() - object.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    object.addX(100 * dtSeconds * dx / dist);
                    object.addY(100 * dtSeconds * dy / dist);
                    if (dx > 0) {
                        if (object.getX() > object.getMotionPath().getEndX()) {
                            object.setForwardMotion(false);
                        }
                    }
                    if (dx < 0) {
                        if (object.getY() < object.getMotionPath().getEndY()) {
                            object.setForwardMotion(false);
                        }
                    }
                } else {
                    double dx = object.getMotionPath().getStartX() - object.getX();
                    double dy = object.getMotionPath().getStartY() - object.getY();
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    object.addX(100 * dtSeconds * dx / dist);
                    object.addY(100 * dtSeconds * dy / dist);

                    if (dx > 0) {
                        if (object.getX() > object.getMotionPath().getStartX()) {
                            object.setForwardMotion(true);
                        }
                    }
                    if (dx < 0) {
                        if (object.getY() < object.getMotionPath().getStartY()) {
                            object.setForwardMotion(true);
                        }
                    }
                }
            }
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
