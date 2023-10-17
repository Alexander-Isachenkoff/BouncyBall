package bouncy.ui;

import bouncy.controller.DefeatController;
import bouncy.model.GameObject;
import bouncy.model.LevelData;
import bouncy.model.Liquid;
import bouncy.model.Player;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

public class PlayingLevel extends Level {

    private final Set<KeyCode> keysPressed = new HashSet<>();
    private final Label fpsLabel = new Label();
    private final int delay = Math.round(1000 / 120f);
    private final Supplier<LevelData> levelDataLoader;
    private Timer timer;
    private long lastUpdate;
    private double dtSeconds;
    private double totalSeconds;

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

        if (keysPressed.contains(KeyCode.LEFT)) {
            player.moveLeft(dtSeconds);
        }

        if (keysPressed.contains(KeyCode.RIGHT)) {
            player.moveRight(dtSeconds);
        }

        if (player.isDead()) {
            timer.cancel();
            Platform.runLater(() -> {
                Stage stage = new Stage(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                Pair<?, Parent> load = ViewUtils.load("fxml/defeat.fxml");
                DefeatController controller = (DefeatController) load.getKey();
                controller.setOnRetry(() -> {
                    load.getValue().getScene().getWindow().hide();
                    restart();
                });
                Scene scene = new Scene(load.getValue());
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            });
        }
    }

    private void restart() {
        keysPressed.clear();
        initLevelData(levelDataLoader.get());
        start();
    }

}
