package bouncy;

import bouncy.model.*;
import bouncy.ui.GameObjectToggleButton;
import bouncy.view.GameObjectNode;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LevelEditor {

    public static final int GRID_SIZE = 40;
    private final Rectangle selector = new Rectangle();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    public Pane levelPane;
    public Tab tilesTab;
    public Accordion tilesAccordion;
    public FlowPane otherPane;
    public FlowPane spikesPane;
    public FlowPane ballsPane;
    private Level level;

    @FXML
    private void initialize() {
        level = new Level();
        level.getLevelData().setName("Level 1");
        levelPane.getChildren().add(level);

        LevelData levelData = LevelData.load("Level 1.xml");
        for (GameObject gameObject : levelData.getGameObjects()) {
            addGameObject(gameObject);
        }

        initGameObjectsList();

        drawGrid();

        level.getChildren().add(selector);
        level.setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / GRID_SIZE) * GRID_SIZE;
            double y = Math.floor(event.getY() / GRID_SIZE) * GRID_SIZE;
            selector.setWidth(GRID_SIZE);
            selector.setHeight(GRID_SIZE);
            GameObject selectedItem = getSelectedGameObject();
            if (selectedItem != null) {
                selector.setFill(new GameObjectNode(selectedItem).getObjectRectangle().getFill());
                selector.setOpacity(0.5);
            } else {
                selector.setFill(Color.LIGHTSKYBLUE);
            }
            selector.setX(x);
            selector.setY(y);
        });

        level.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            int xIndex = (int) Math.floor(event.getX() / GRID_SIZE);
            int yIndex = (int) Math.floor(event.getY() / GRID_SIZE);
            double x = xIndex * GRID_SIZE;
            double y = yIndex * GRID_SIZE;
            GameObject selectedItem = getSelectedGameObject();
            if (selectedItem != null) {
                GameObject gameObject;
                try {
                    gameObject = selectedItem.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                gameObject.setX(x);
                gameObject.setY(y);
                gameObject.setWidth(GRID_SIZE);
                gameObject.setHeight(GRID_SIZE);
                gameObject.setImagePath(selectedItem.getImagePath());
                addGameObject(gameObject);
            }
        });
    }

    private void addGameObjectToList(Pane pane, GameObject gameObject) {
        ToggleButton toggleButton = new GameObjectToggleButton(gameObject);
        toggleButton.setToggleGroup(toggleGroup);
        pane.getChildren().add(toggleButton);
    }

    private GameObject getSelectedGameObject() {
        GameObjectToggleButton selectedToggle = (GameObjectToggleButton) toggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            return selectedToggle.getGameObject();
        } else {
            return null;
        }
    }

    private void initGameObjectsList() {
        double size = GRID_SIZE;
        List<BlockFamily> blockFamilies = loadBlockFamilies();
        for (BlockFamily blockFamily : blockFamilies) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(StringUtils.capitalize(blockFamily.getName()));
            FlowPane flowPane = new FlowPane();
            flowPane.getStyleClass().add("gameObjectsFlowPane");
            titledPane.setContent(flowPane);

            for (String imageName : blockFamily.getImageNames()) {
                Block gameObject = new Block(Paths.get(blockFamily.getImagesPack(), imageName).toString(), size, size);
                addGameObjectToList(flowPane, gameObject);
            }
            tilesAccordion.getPanes().add(titledPane);
        }

        String spikesPackPath = "images/spikes";
        List<String> fileNames = FileUtils.getPackFileNames(spikesPackPath);
        for (String fileName : fileNames) {
            addGameObjectToList(spikesPane, new Spikes(new File(spikesPackPath, fileName).getPath(), size, size));
        }

        String ballsPackPath = "images/balls";
        List<String> ballsFileNames = FileUtils.getPackFileNames(ballsPackPath);
        for (String fileName : ballsFileNames) {
            addGameObjectToList(ballsPane, new Player(new File(ballsPackPath, fileName).getPath(), size, size));
        }

        addGameObjectToList(otherPane, new Star(size, size));
    }

    private List<BlockFamily> loadBlockFamilies() {
        String tilesDir = "images/blocks";
        File blocksDir = new File(tilesDir);
        return Arrays.stream(Optional.ofNullable(blocksDir.listFiles()).orElse(new File[0]))
                .map(file -> new BlockFamily(file.getName(), file.getPath()))
                .collect(Collectors.toList());
    }

    private void drawGrid() {
        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = GRID_SIZE * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartY(y);
            line.setEndY(y);
            line.setEndX(3000);
            level.getChildren().add(line);
        }

        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = GRID_SIZE * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartX(y);
            line.setEndX(y);
            line.setEndY(3000);
            level.getChildren().add(line);
        }
    }

    private void addGameObject(GameObject gameObject) {
        GameObjectNode gameObjectNode = level.add(gameObject);
        gameObjectNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                level.remove(gameObject);
            }
        });
    }

    @FXML
    private void onSave() {
        level.getLevelData().save();
    }

    @FXML
    private void onStart() {
        Level level1 = new Level();
        for (GameObject gameObject : level.getLevelData().getGameObjects()) {
            level1.add(gameObject);
        }
        Scene scene = levelPane.getScene();
        scene.setRoot(level1);
        level1.start();
    }

}
