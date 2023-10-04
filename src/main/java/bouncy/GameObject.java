package bouncy;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

public abstract class GameObject {

    @Getter
    private final Rectangle rect;

    protected GameObject(String imageFileName) {
        this(imageFileName, 30, 30);
    }

    protected GameObject(String imageFileName, int width, int height) {
        rect = new Rectangle(width, height);
        Image image = new Image(Main.class.getResourceAsStream(imageFileName));
        setImage(image);
    }

    private void setImage(Image image) {
        rect.setFill(new ImagePattern(image));
    }

    public void addX(double x) {
        setX(rect.getX() + x);
    }

    public void addY(double y) {
        setY(rect.getY() + y);
    }

    public double getX() {
        return rect.getX();
    }

    public void setX(double x) {
        rect.setX(x);
    }

    public double getY() {
        return rect.getY();
    }

    public void setY(double y) {
        rect.setY(y);
    }

    public void setPosition(double x, double y) {
        rect.setX(x);
        rect.setY(y);
    }

    public double getWidth() {
        return rect.getWidth();
    }

    public double getHeight() {
        return rect.getHeight();
    }

    public double getRightX() {
        return getX() + getWidth();
    }

    public double getBottomY() {
        return getY() + getHeight();
    }

    public boolean intersects(GameObject object) {
        return object.getX() < getRightX() && object.getX() + object.getWidth() > getX() &&
                object.getY() < getBottomY() && object.getY() + object.getHeight() > getY();
    }

}
