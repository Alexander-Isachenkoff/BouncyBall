package bouncy;

import bouncy.model.*;
import bouncy.view.GameObjectNode;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class LevelEditor {

    public static final int GRID_SIZE = 40;
    private final Rectangle selector = new Rectangle();
    public ListView<GameObject> itemsListView;
    public Pane levelPane;
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

        itemsListView.getItems().add(new Block());
        itemsListView.getItems().add(new Star());
        itemsListView.getItems().add(new Player());

        itemsListView.setCellFactory(param -> new ListCell<GameObject>() {
            @Override
            protected void updateItem(GameObject item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setGraphic(new GameObjectNode(item));
                } else {
                    setGraphic(null);
                }
            }
        });

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

        level.getChildren().add(selector);
        level.setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / GRID_SIZE) * GRID_SIZE;
            double y = Math.floor(event.getY() / GRID_SIZE) * GRID_SIZE;
            selector.setWidth(GRID_SIZE);
            selector.setHeight(GRID_SIZE);
            GameObject selectedItem = itemsListView.getSelectionModel().getSelectedItem();
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
            GameObject selectedGameObject = itemsListView.getSelectionModel().getSelectedItem();
            if (selectedGameObject != null) {
                GameObject gameObject;
                try {
                    gameObject = selectedGameObject.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                gameObject.setX(x);
                gameObject.setY(y);
                addGameObject(gameObject);
            }
        });
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
