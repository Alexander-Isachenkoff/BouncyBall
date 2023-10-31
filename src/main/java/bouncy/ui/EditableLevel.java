package bouncy.ui;

import bouncy.model.GameObject;
import bouncy.model.MotionPath;
import bouncy.model.MotionPoint;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.PseudoClass;
import javafx.scene.Cursor;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditableLevel extends Level {

    private final Rectangle selector = new Rectangle();
    private final List<Line> lines = new ArrayList<>();
    private final SelectionModel<GameObjectNode> levelSelectionModel = new SingleSelectionModel<GameObjectNode>() {
        @Override
        protected GameObjectNode getModelItem(int index) {
            return (GameObjectNode) getChildren().stream()
                    .filter(node -> node instanceof GameObjectNode)
                    .collect(Collectors.toList())
                    .get(index);
        }

        @Override
        protected int getItemCount() {
            return (int) getChildren().stream()
                    .filter(node -> node instanceof GameObjectNode)
                    .count();
        }
    };
    @Getter
    private final SimpleObjectProperty<GameObjectToggleButton> selectedToggleProperty = new SimpleObjectProperty<>();
    @Getter
    @Setter
    private int gridSize;

    private GameObject gameObjectToPlace;

    public EditableLevel() {
        this(40);
    }

    public EditableLevel(int gridSize) {
        this.gridSize = gridSize;

        getChildren().add(selector);
        selector.setOpacity(0.5);
        selector.setFill(Color.LIGHTSKYBLUE);
        selector.setWidth(this.gridSize);
        selector.setHeight(this.gridSize);

        setOnMouseMoved(event -> {
            double x = Math.floor(event.getX() / this.gridSize) * this.gridSize;
            double y = Math.floor(event.getY() / this.gridSize) * this.gridSize;
            selector.setX(x);
            selector.setY(y);
        });

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                int xIndex = (int) Math.floor(event.getX() / this.gridSize);
                int yIndex = (int) Math.floor(event.getY() / this.gridSize);
                double x = xIndex * this.gridSize;
                double y = yIndex * this.gridSize;
                if (getLevelData().getGameObjects().stream()
                        .filter(gameObject -> gameObject.getX() == x)
                        .anyMatch(gameObject -> gameObject.getY() == y)) {
                    return;
                }
                if (gameObjectToPlace != null) {
                    GameObject gameObject = gameObjectToPlace.clone();
                    gameObject.setX(x);
                    gameObject.setY(y);
                    gameObject.setWidth(this.gridSize);
                    gameObject.setHeight(this.gridSize);
                    GameObjectNode gameObjectNode = add(gameObject);
                    if (gameObject instanceof MotionPoint) {
                        MotionPoint motionPoint = (MotionPoint) gameObject;
                        addMotionPoint(getNode(motionPoint.getParent()), gameObjectNode, motionPoint);
                        GameObject parent = motionPoint.getParent();
                        parent.setMotionPath(new MotionPath(parent.getX(), parent.getY(), x, y));
                    }
                }
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                if (selectedToggleProperty.get() != null) {
                    selectedToggleProperty.get().setSelected(false);
                }
            }
        });

        widthProperty().addListener((observable, oldValue, newValue) -> drawGrid());
        heightProperty().addListener((observable, oldValue, newValue) -> drawGrid());

        levelSelectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
            }
            if (newValue != null) {
                newValue.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
                if (selectedToggleProperty.get() != null) {
                    selectedToggleProperty.get().setSelected(false);
                }
            }
        });

        selectedToggleProperty.addListener((observable, oldValue, newValue) -> {
            setGameObjectToPlace((newValue != null) ? newValue.getGameObject() : null);
            if (newValue != null) {
                levelSelectionModel.select(null);
            }
        });
    }

    public void setGameObjectToPlace(GameObject gameObjectToPlace) {
        this.gameObjectToPlace = gameObjectToPlace;
        if (gameObjectToPlace != null) {
            selector.setFill(new ImagePattern(ImageManager.getImage(gameObjectToPlace.getImagePath())));
        } else {
            selector.setFill(Color.LIGHTSKYBLUE);
        }
    }

    @Override
    public GameObjectNode add(GameObject gameObject) {
        GameObjectNode gameObjectNode = super.add(gameObject);
        gameObjectNode.setCursor(Cursor.HAND);

        if (gameObject.getMotionPath() != null) {
            MotionPoint motionPoint = new MotionPoint(gameObject);
            motionPoint.setX(gameObject.getMotionPath().getEndX());
            motionPoint.setY(gameObject.getMotionPath().getEndY());
            motionPoint.setWidth(gridSize);
            motionPoint.setHeight(gridSize);
            GameObjectNode motionPointNode = add(motionPoint);
            addMotionPoint(gameObjectNode, motionPointNode, motionPoint);
        }

        gameObjectNode.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                levelSelectionModel.select(gameObjectNode);
                setGameObjectToPlace(new MotionPoint(gameObject));
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                if (gameObject instanceof MotionPoint) {
                    ((MotionPoint) gameObject).getParent().setMotionPath(null);
                }
                remove(gameObject);
            }
        });
        return gameObjectNode;
    }

    private void addMotionPoint(GameObjectNode parentNode, GameObjectNode motionPointNode, MotionPoint motionPoint) {
        GameObject parent = motionPoint.getParent();
        Line line = new Line(
                parent.getX() + parent.getWidth() / 2,
                parent.getY() + parent.getHeight() / 2,
                motionPoint.getX() + motionPoint.getWidth() / 2,
                motionPoint.getY() + motionPoint.getHeight() / 2
        );
        line.setStroke(Color.GRAY);
        getChildren().add(line);
        motionPointNode.parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                Platform.runLater(() -> getChildren().remove(line));
            }
        });
        parentNode.parentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                remove(motionPoint);
            }
        });
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
