package bouncy.ui;


import bouncy.AppProperties;
import bouncy.model.GameObject;
import bouncy.model.Liquid;
import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class GameObjectNode extends Region {

    private final GameObject gameObject;
    private final Rectangle colliderRectangle = new Rectangle();
    private final Image image;

    public GameObjectNode(GameObject gameObject) {
        this.gameObject = gameObject;
        this.setLayoutX(gameObject.getX());
        this.setLayoutY(gameObject.getY());

        this.setPrefWidth(gameObject.getWidth());
        this.setPrefHeight(gameObject.getWidth());

        colliderRectangle.setX(gameObject.getColliderX());
        colliderRectangle.setY(gameObject.getColliderY());
        colliderRectangle.setWidth(gameObject.getColliderWidth());
        colliderRectangle.setHeight(gameObject.getColliderHeight());
        colliderRectangle.setFill(Color.TRANSPARENT);
        getChildren().add(colliderRectangle);

        setColliderVisible(AppProperties.getCollidersProperty().get());
        AppProperties.getCollidersProperty().addListener((observable, oldValue, newValue) -> setColliderVisible(newValue));

        image = ImageManager.getImage(gameObject.getImageData().getImagePath());
        updateBackground();

        gameObject.setOnXChanged(this::setLayoutX);
        gameObject.setOnYChanged(this::setLayoutY);
        gameObject.setOnWidthChanged(this::setPrefWidth);
        gameObject.setOnHeightChanged(this::setPrefHeight);
        gameObject.setOnAngleChanged(this::setRotate);

        if (gameObject instanceof Liquid) {
            ((Liquid) gameObject).setOnStateTimeChanged(aDouble -> updateBackground());
        }
    }

    private void updateBackground() {
        double horizontalPosition = 0;
        if (gameObject instanceof Liquid) {
            horizontalPosition = ((Liquid) gameObject).getStateTime() * 15;
        }
        BackgroundPosition position = new BackgroundPosition(Side.LEFT, horizontalPosition, false, Side.TOP, 0, false);
        BackgroundSize size = new BackgroundSize(gameObject.getWidth(), gameObject.getHeight(), false, false, false, false);
        setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, position, size)));
    }

    private void setColliderVisible(boolean visible) {
        if (visible) {
            colliderRectangle.setStroke(Color.RED);
        } else {
            colliderRectangle.setStroke(Color.TRANSPARENT);
        }
    }

}
