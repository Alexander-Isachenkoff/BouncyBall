package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class FxmlBox extends VBox {

    public FxmlBox(String fxml) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
