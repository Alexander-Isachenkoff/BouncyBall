package bouncy.view;


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
    private final Rectangle objectRectangle = new Rectangle();
    private final Rectangle colliderRectangle = new Rectangle();

    public GameObjectNode(GameObject gameObject) {
        this.gameObject = gameObject;
        this.setLayoutX(gameObject.getX());
        this.setLayoutY(gameObject.getY());

        objectRectangle.setWidth(gameObject.getWidth());
        objectRectangle.setHeight(gameObject.getWidth());

        colliderRectangle.setX(gameObject.getColliderX());
        colliderRectangle.setY(gameObject.getColliderY());
        colliderRectangle.setWidth(gameObject.getColliderWidth());
        colliderRectangle.setHeight(gameObject.getColliderHeight());
        colliderRectangle.setFill(Color.TRANSPARENT);
        colliderRectangle.setStroke(Color.LAWNGREEN);

        Image image = ImageManager.getImage(gameObject.getImageData().getImagePath());
        objectRectangle.setFill(new ImagePattern(image));

        getChildren().add(objectRectangle);
        getChildren().add(colliderRectangle);

        gameObject.setOnXChanged(this::setLayoutX);
        gameObject.setOnYChanged(this::setLayoutY);
        gameObject.setOnWidthChanged(objectRectangle::setWidth);
        gameObject.setOnHeightChanged(objectRectangle::setHeight);
    }

}
