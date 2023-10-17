package bouncy.ui;

import bouncy.controller.DefeatController;
import bouncy.controller.WinController;
import bouncy.model.LevelData;
import bouncy.model.LevelProgressData;
import bouncy.model.LevelsProgressData;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

public class CampaignLevel extends PlayingLevel {

    private final LevelProgressData levelProgressData;

    public CampaignLevel(LevelProgressData levelProgressData) {
        super(() -> LevelData.load(levelProgressData.getFileName()));
        this.levelProgressData = levelProgressData;
    }

    @Override
    protected void onDead() {
        Platform.runLater(() -> {
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            Pair<DefeatController, Parent> load = ViewUtils.loadWithController("fxml/defeat.fxml");
            DefeatController controller = load.getKey();
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

    @Override
    protected void onWin() {
        LevelsProgressData progressData = LevelsProgressData.load();
        progressData.setDone(levelProgressData.getIndex(), true);
        progressData.save();

        Platform.runLater(() -> {
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            Pair<WinController, Parent> load = ViewUtils.loadWithController("fxml/win.fxml");
            WinController controller = load.getKey();
            controller.setOnRetry(() -> {
                load.getValue().getScene().getWindow().hide();
                restart();
            });
            controller.setOnNext(() -> {
                load.getValue().getScene().getWindow().hide();
                PlayingLevel playingLevel = new CampaignLevel(progressData.get(levelProgressData.getIndex() + 1));
                this.getScene().setRoot(playingLevel);
                playingLevel.start();
            });
            Scene scene = new Scene(load.getValue());
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        });
    }

}
