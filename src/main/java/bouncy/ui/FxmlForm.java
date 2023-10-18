package bouncy.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

public class FxmlForm extends FxmlBox {

    @Getter
    private final Stage stage;

    public FxmlForm(String fxml) {
        super(fxml);
        Scene scene = new Scene(this);
        stage = new Stage();
        stage.setScene(scene);
    }

    public void show() {
        stage.show();
    }

}
