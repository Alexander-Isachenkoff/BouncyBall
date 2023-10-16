package bouncy.ui;

import bouncy.model.GameObject;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EditableLevel extends Level {

    private final Rectangle selector = new Rectangle();
    private final List<Line> lines = new ArrayList<>();
    @Getter
    @Setter
    private int gridSize;
    @Setter
    private Supplier<GameObject> gameObjectFactory = () -> null;

    public EditableLevel() {
        this(40);
    }

    public EditableLevel(int gridSize) {
        this.gridSize = gridSize;

        selector.setOpacity(0.5);
        getChildren().add(selector);

        setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / this.gridSize) * this.gridSize;
            double y = Math.floor(event.getY() / this.gridSize) * this.gridSize;
            selector.setWidth(this.gridSize);
            selector.setHeight(this.gridSize);
            GameObject selectedItem = gameObjectFactory.get();
            if (selectedItem != null) {
                selector.setFill(new ImagePattern(ImageManager.getImage(selectedItem.getImagePath())));
            } else {
                selector.setFill(Color.LIGHTSKYBLUE);
            }
            selector.setX(x);
            selector.setY(y);
        });

        setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY) {
                return;
            }

            int xIndex = (int) Math.floor(event.getX() / this.gridSize);
            int yIndex = (int) Math.floor(event.getY() / this.gridSize);
            double x = xIndex * this.gridSize;
            double y = yIndex * this.gridSize;
            GameObject selectedItem = gameObjectFactory.get();
            if (selectedItem != null) {
                GameObject gameObject;
                try {
                    gameObject = selectedItem.getClass().newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                gameObject.setX(x);
                gameObject.setY(y);
                gameObject.setWidth(this.gridSize);
                gameObject.setHeight(this.gridSize);
                gameObject.setImagePath(selectedItem.getImagePath());
                add(gameObject);
            }
        });

        widthProperty().addListener((observable, oldValue, newValue) -> drawGrid());
        heightProperty().addListener((observable, oldValue, newValue) -> drawGrid());
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
        getChildren().removeAll(lines);
        lines.clear();
        drawHLines();
        drawVLines();
    }

    private void drawHLines() {
        for (int i = 1; i <= getHeight() / gridSize; i++) {
            Line line = new Line();
            int y = gridSize * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartY(y);
            line.setEndY(y);
            line.setEndX(getWidth() - 1);
            getChildren().add(0, line);
            lines.add(line);
        }
    }

    private void drawVLines() {
        for (int i = 1; i <= getWidth() / gridSize; i++) {
            Line line = new Line();
            int y = gridSize * i;
            line.setStrokeWidth(1);
            line.setStroke(Color.LIGHTGRAY);
            line.setStartX(y);
            line.setEndX(y);
            line.setEndY(getHeight() - 1);
            getChildren().add(0, line);
            lines.add(line);
        }
    }

}
