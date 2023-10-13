package bouncy.ui;

import bouncy.FileUtils;
import bouncy.model.Category;
import bouncy.model.GameObject;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.List;

public class GameObjectsFlowPane extends FlowPane {

    private final ToggleGroup toggleGroup;

    public GameObjectsFlowPane(ToggleGroup toggleGroup, Category category, double gridSize) {
        this.toggleGroup = toggleGroup;
        getStyleClass().add("gameObjectsFlowPane");
        List<String> fileNames = FileUtils.getPackFileNames(category.getPack());
        for (String fileName : fileNames) {
            try {
                Class<?> aClass = Class.forName("bouncy.model." + category.getClassName());
                GameObject gameObject = (GameObject) aClass.newInstance();
                gameObject.setWidth(gridSize);
                gameObject.setHeight(gridSize);
                gameObject.setImagePath(new File(category.getPack(), fileName).getPath());
                addGameObjectToList(this, gameObject);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addGameObjectToList(Pane pane, GameObject gameObject) {
        ToggleButton toggleButton = new GameObjectToggleButton(gameObject);
        toggleButton.setToggleGroup(toggleGroup);
        pane.getChildren().add(toggleButton);
    }

}
