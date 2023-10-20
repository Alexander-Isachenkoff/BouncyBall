package bouncy.ui;

import bouncy.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomLevels extends FxmlBox {

    @FXML
    private ScrollPane scrollPane;

    public CustomLevels() {
        super("fxml/custom_levels.fxml");
    }

    @FXML
    private void initialize() {
        File[] levelsFiles = Optional.ofNullable(new File("data/user_levels").listFiles()).orElse(new File[0]);
        List<String> levelFileNames = Arrays.stream(levelsFiles)
                .filter(File::isFile)
                .map(File::getAbsolutePath)
                .collect(Collectors.toList());
        LevelsFlowPane levelsFlowPane = new LevelsFlowPane();
        levelsFlowPane.loadLevels(levelFileNames, CustomLevelCell::new);
        scrollPane.setContent(levelsFlowPane);
    }

    @FXML
    private void onMenu() {
        Main.toMainMenu();
    }

}
