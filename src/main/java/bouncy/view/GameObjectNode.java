package bouncy.view;


import bouncy.Main;
import bouncy.model.GameObject;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

public class GameObjectNode extends Rectangle {

    @Getter
    private final GameObject gameObject;

    public GameObjectNode(GameObject gameObject) {
        this.gameObject = gameObject;
        setX(gameObject.getX());
        setY(gameObject.getY());
        setWidth(gameObject.getWidth());
        setHeight(gameObject.getWidth());

        Image image = new Image(Main.class.getResourceAsStream(gameObject.getImagePath()));
        setFill(new ImagePattern(image));

        gameObject.setOnXChanged(this::setX);
        gameObject.setOnYChanged(this::setY);
        gameObject.setOnWidthChanged(this::setWidth);
        gameObject.setOnHeightChanged(this::setHeight);
    }

}
