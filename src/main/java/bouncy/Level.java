package bouncy;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

public class Level extends Pane {

    private final List<GameObject<?>> gameObjects = new ArrayList<>();
    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final Label scoreLabel;
    private long lastUpdate;
    private long dtMills;
    private double dtSecs;
    private int score = 0;

    public Level() {
        scoreLabel = new Label();
        getChildren().add(scoreLabel);
        setOnKeyPressed(event -> {
            keysPressed.add(event.getCode());
        });
        setOnKeyReleased(event -> {
            keysPressed.remove(event.getCode());
        });
    }

    public void add(GameObject<?> object) {
        gameObjects.add(object);
        Platform.runLater(() -> getChildren().add(object.getNode()));
    }

    public void remove(GameObject<?> object) {
        gameObjects.remove(object);
        Platform.runLater(() -> getChildren().remove(object.getNode()));
    }

    private <T extends GameObject<?>> List<T> getObjects(Class<T> tClass) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getClass() == tClass)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    private <T extends GameObject<?>> T getObject(Class<T> tClass) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getClass() == tClass)
                .map(gameObject -> (T) gameObject)
                .findFirst()
                .get();
    }

    private Ball getBall() {
        return getObject(Ball.class);
    }

    public void start() {
        Thread thread = new Thread(() -> {
            lastUpdate = System.currentTimeMillis();

            while (true) {
                requestFocus();
                dtMills = System.currentTimeMillis() - lastUpdate;
                dtSecs = dtMills / 1000.0;
                lastUpdate = System.currentTimeMillis();
                processBall();
                generateStars();
                try {
                    Thread.sleep(Math.round(1000 / 60f));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        });
        thread.setDaemon(true);
        thread.start();
    }

    private void generateStars() {
        if (System.currentTimeMillis() % 50 == 0) {
            Random random = new Random();
            List<Block> blocks = getObjects(Block.class);
            Block block = blocks.get(random.nextInt(blocks.size()));
            Star star = new Star();
            star.setPosition(block.getX(), block.getY() - star.getHeight());
            add(star);
        }
    }


    private void processBall() {
        Ball ball = getBall();
        List<Block> blocks = getObjects(Block.class);

        blocks.forEach(block -> block.getNode().setFill(Color.LIGHTGRAY));

        List<Block> bottomBlocks = blocks.stream()
                .filter(block -> block.getX() <= ball.getRightX() && block.getX() + block.getWidth() >= ball.getLeftX())
                .filter(block -> block.getY() <= ball.getY() + ball.getHeight() / 2. && block.getY() + block.getHeight() > ball.getY())
                .peek(block -> {
                    block.getNode().setFill(Color.RED);
                })
                .collect(Collectors.toList());

        if (!bottomBlocks.isEmpty() && ball.getVSpeed() >= 0) {
            ball.setVSpeed(-300);
        }

        ball.setVSpeed(ball.getVSpeed() + 10);

        ball.addY(ball.getVSpeed() * dtSecs);

        if (keysPressed.contains(KeyCode.LEFT)) {
            List<Block> leftBlocks = blocks.stream()
                    .filter(block -> block.getY() < ball.getY() && block.getY() + block.getWidth() > ball.getY())
                    .filter(block -> block.getX() + block.getWidth() >= ball.getLeftX() && block.getX() < ball.getX())
                    .peek(block -> {
                        block.getNode().setFill(Color.BLUE);
                    })
                    .collect(Collectors.toList());
            if (leftBlocks.isEmpty()) {
                ball.addX(-ball.getSideSpeed() * dtSecs);
            }
        }

        if (keysPressed.contains(KeyCode.RIGHT)) {
            List<Block> rightBlocks = blocks.stream()
                    .filter(block -> block.getY() < ball.getY() && block.getY() + block.getWidth() > ball.getY())
                    .filter(block -> block.getX() <= ball.getRightX() && block.getX() + block.getWidth() > ball.getX())
                    .peek(block -> {
                        block.getNode().setFill(Color.BLUE);
                    })
                    .collect(Collectors.toList());
            if (rightBlocks.isEmpty()) {
                ball.addX(ball.getSideSpeed() * dtSecs);
            }
        }

        getObjects(Star.class).stream()
                .filter(ball::intersects)
                .forEach(object -> {
                    remove(object);
                    score++;
                });

        Platform.runLater(() -> scoreLabel.setText("Score: " + score));
    }

}
