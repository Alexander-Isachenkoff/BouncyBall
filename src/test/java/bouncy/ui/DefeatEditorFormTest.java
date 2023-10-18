package bouncy.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class DefeatEditorFormTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new DefeatEditorForm(null).show();
    }

    @Test
    void run() {
        launch();
    }

}
