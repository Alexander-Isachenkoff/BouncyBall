package bouncy.view;


import bouncy.AppProperties;
import bouncy.ImageManager;
import bouncy.model.GameObject;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

@Getter
public class GameObjectNode extends Region {

    private final GameObject gameObject;
    private final Rectangle imageRectangle = new Rectangle();
    private final Rectangle colliderRectangle = new Rectangle();

    public GameObjectNode(GameObject gameObject) {
        this.gameObject = gameObject;
        this.setLayoutX(gameObject.getX());
        this.setLayoutY(gameObject.getY());

        imageRectangle.setWidth(gameObject.getWidth());
        imageRectangle.setHeight(gameObject.getWidth());

        colliderRectangle.setX(gameObject.getColliderX());
        colliderRectangle.setY(gameObject.getColliderY());
        colliderRectangle.setWidth(gameObject.getColliderWidth());
        colliderRectangle.setHeight(gameObject.getColliderHeight());
        colliderRectangle.setFill(Color.TRANSPARENT);

        setColliderVisible(AppProperties.collidersProperty.get());
        AppProperties.collidersProperty.addListener((observable, oldValue, newValue) -> setColliderVisible(newValue));

        Image image = ImageManager.getImage(gameObject.getImageData().getImagePath());
        imageRectangle.setFill(new ImagePattern(image));

        getChildren().add(imageRectangle);
        getChildren().add(colliderRectangle);

        gameObject.setOnXChanged(this::setLayoutX);
        gameObject.setOnYChanged(this::setLayoutY);
        gameObject.setOnWidthChanged(imageRectangle::setWidth);
        gameObject.setOnHeightChanged(imageRectangle::setHeight);
        gameObject.setOnAngleChanged(imageRectangle::setRotate);
    }

    private void setColliderVisible(boolean visible) {
        if (visible) {
            colliderRectangle.setStroke(Color.RED);
        } else {
            colliderRectangle.setStroke(Color.TRANSPARENT);
        }
    }

}
