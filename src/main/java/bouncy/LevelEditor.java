package bouncy;

import bouncy.model.*;
import bouncy.ui.GameObjectToggleButton;
import bouncy.view.GameObjectNode;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LevelEditor {

    public static final int GRID_SIZE = 30;
    private final Rectangle selector = new Rectangle();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    public Pane levelPane;
    public Tab tilesTab;
    public Accordion tilesAccordion;
    public FlowPane otherPane;
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
                selector.setFill(new GameObjectNode(selectedItem).getFill());
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
                gameObject.setImagePack(selectedItem.getImagePack());
                gameObject.setImageName(selectedItem.getImageName());
                addGameObject(gameObject);
            }
        });

        addGameObjectToList(otherPane, new Player(GRID_SIZE, GRID_SIZE));
        addGameObjectToList(otherPane, new Star(GRID_SIZE, GRID_SIZE));
    }

    private void addGameObjectToList(Pane pane, GameObject gameObject) {
        ToggleButton toggleButton = new GameObjectToggleButton(gameObject);
        toggleButton.setToggleGroup(toggleGroup);
        pane.getChildren().add(toggleButton);
    }

    private GameObject getSelectedGameObject() {
        GameObject selectedItem = null;
        GameObjectToggleButton selectedToggle = (GameObjectToggleButton) toggleGroup.getSelectedToggle();
        if (selectedToggle != null) {
            selectedItem = selectedToggle.getGameObject();
        }
        return selectedItem;
    }

    private void initGameObjectsList() {
        List<BlockFamily> blockFamilies = loadBlockFamilies();
        for (BlockFamily blockFamily : blockFamilies) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(blockFamily.getName());
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(5);
            flowPane.setVgap(5);
            flowPane.setPadding(new Insets(5));
            titledPane.setContent(flowPane);

            for (String imageName : blockFamily.getImageNames()) {
                Block gameObject = new Block(blockFamily.getImagesPack(), imageName, 35, 35);
                addGameObjectToList(flowPane, gameObject);
            }
            tilesAccordion.getPanes().add(titledPane);
        }
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
