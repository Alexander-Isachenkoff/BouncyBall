package bouncy;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

public class LevelEditor {

    public static final int GRID_SIZE = 30;
    public ListView<GameObject> itemsListView;
    public Pane levelPane;

    private Rectangle selector = new Rectangle();
    private Map<Rectangle, GameObject> levelData = new HashMap<>();

    @FXML
    private void initialize() {
        itemsListView.getItems().add(new Block());
        itemsListView.getItems().add(new Star());
        itemsListView.getItems().add(new Ball());

        itemsListView.setCellFactory(param -> new ListCell<GameObject>() {
            @Override
            protected void updateItem(GameObject item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setGraphic(item.getRect());
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
            levelPane.getChildren().add(line);
        }

        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = GRID_SIZE * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartX(y);
            line.setEndX(y);
            line.setEndY(3000);
            levelPane.getChildren().add(line);
        }

        levelPane.getChildren().add(selector);
        levelPane.setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / GRID_SIZE) * GRID_SIZE;
            double y = Math.floor(event.getY() / GRID_SIZE) * GRID_SIZE;
            selector.setWidth(GRID_SIZE);
            selector.setHeight(GRID_SIZE);
            GameObject selectedItem = itemsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                selector.setFill(selectedItem.getRect().getFill());
                selector.setOpacity(0.5);
            } else {
                selector.setFill(Color.LIGHTSKYBLUE);
            }
            selector.setX(x);
            selector.setY(y);
        });

        levelPane.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            int xIndex = (int) Math.floor(event.getX() / GRID_SIZE);
            int yIndex = (int) Math.floor(event.getY() / GRID_SIZE);
            double x = xIndex * GRID_SIZE;
            double y = yIndex * GRID_SIZE;
            GameObject selectedItem = itemsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Rectangle rect = new Rectangle(GRID_SIZE, GRID_SIZE, selectedItem.getRect().getFill());
                rect.setX(x);
                rect.setY(y);
                levelPane.getChildren().add(rect);

                rect.setOnMouseClicked(event1 -> {
                    if (event1.getButton() == MouseButton.SECONDARY) {
                        levelData.remove(rect);
                        levelPane.getChildren().remove(rect);
                    }
                });

                GameObject gameObject;
                try {
                    gameObject = selectedItem.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                levelData.put(rect, gameObject);
            }
        });
    }

}
