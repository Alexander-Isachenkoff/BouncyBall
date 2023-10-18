package bouncy.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import lombok.Setter;

import java.util.function.Consumer;

public class InputNameForm extends FxmlForm {

    @FXML
    private TextField textField;
    @FXML
    private Button saveButton;
    @Setter
    private Consumer<String> onSave = s -> {
    };

    public InputNameForm() {
        super("fxml/name_input.fxml");
        getScene().setFill(Color.TRANSPARENT);
        this.getStage().initStyle(StageStyle.TRANSPARENT);
        this.getStage().initModality(Modality.APPLICATION_MODAL);
    }

    @FXML
    private void initialize() {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(textField.getText().trim().isEmpty());
        });
    }

    @FXML
    private void onSave() {
        this.getStage().close();
        onSave.accept(textField.getText());
    }

}
