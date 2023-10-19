package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.util.Optional;

public class UserLevels extends FxmlBox {

    @FXML
    private FlowPane levelsFlowPane;

    public UserLevels() {
        super("fxml/user_levels.fxml");
    }

    @FXML
    private void initialize() {
        loadLevels();
    }

    private void loadLevels() {
        File[] levelsFiles = Optional.ofNullable(new File("data/user_levels").listFiles()).orElse(new File[0]);
        for (File levelsFile : levelsFiles) {
            if (levelsFile.isFile()) {
                FlowLevelCell cell = new FlowLevelCell(levelsFile.getAbsolutePath());
                levelsFlowPane.getChildren().add(cell);
            }
        }
    }

    @FXML
    private void onMenu() {
        Main.toMainMenu();
    }

}
