package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class DefeatEditorForm extends VBox {

    private final PlayingLevel level;
    private final Stage stage;

    public DefeatEditorForm(PlayingLevel level) {
        this.level = level;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("fxml/defeat_editor.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(this);
        scene.setFill(Color.TRANSPARENT);

        stage = new Stage(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

    @FXML
    private void onRetry() {
        stage.close();
        level.restart();
    }

    @FXML
    private void onEditor() {
        stage.close();
        Main.getStage().getScene().setRoot(ViewUtils.loadLevelEditor());
    }

}
