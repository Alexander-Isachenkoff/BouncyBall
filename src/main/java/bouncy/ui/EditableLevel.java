package bouncy.ui;

import bouncy.ImageManager;
import bouncy.model.GameObject;
import bouncy.view.GameObjectNode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public abstract class EditableLevel extends Level {

    private final Rectangle selector = new Rectangle();
    private final int gridSize;

    public EditableLevel(int gridSize) {
        this.gridSize = gridSize;
        drawGrid();

        getChildren().add(selector);

        setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / gridSize) * gridSize;
            double y = Math.floor(event.getY() / gridSize) * gridSize;
            selector.setWidth(gridSize);
            selector.setHeight(gridSize);
            GameObject selectedItem = getSelectedGameObject();
            if (selectedItem != null) {
                selector.setFill(new ImagePattern(ImageManager.getImage(selectedItem.getImagePath())));
            } else {
                selector.setFill(Color.LIGHTSKYBLUE);
            }
            selector.setOpacity(0.5);
            selector.setX(x);
            selector.setY(y);
        });

        setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            int xIndex = (int) Math.floor(event.getX() / gridSize);
            int yIndex = (int) Math.floor(event.getY() / gridSize);
            double x = xIndex * gridSize;
            double y = yIndex * gridSize;
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
                gameObject.setWidth(gridSize);
                gameObject.setHeight(gridSize);
                gameObject.setImagePath(selectedItem.getImagePath());
                add(gameObject);
            }
        });
    }

    @Override
    public GameObjectNode add(GameObject gameObject) {
        GameObjectNode gameObjectNode = super.add(gameObject);
        gameObjectNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                remove(gameObject);
            }
        });
        return gameObjectNode;
    }

    private void drawGrid() {
        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = gridSize * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartY(y);
            line.setEndY(y);
            line.setEndX(3000);
            getChildren().add(line);
        }

        for (int i = 1; i <= 100; i++) {
            Line line = new Line();
            int y = gridSize * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartX(y);
            line.setEndX(y);
            line.setEndY(3000);
            getChildren().add(line);
        }
    }

    public abstract GameObject getSelectedGameObject();

}
