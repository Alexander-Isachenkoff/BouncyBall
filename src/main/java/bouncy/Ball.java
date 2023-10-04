package bouncy;

import javafx.scene.shape.Circle;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class Ball extends GameObject<Circle> {

    private final double sideSpeed = 100;
    private double vSpeed = 0;

    public Ball() throws IOException {
        super("ball.fxml");
    }

    public double getLeftX() {
        return getX() - getWidth() / 2.;
    }

    public double getRightX() {
        return getX() + getWidth() / 2.;
    }

    public double getTopY() {
        return getY() - getHeight() / 2.;
    }

    public double getBottomY() {
        return getY() + getHeight() / 2.;
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    public boolean collides(GameObject<?> object) {
        return object.getX() <= getRightX() && object.getX() + object.getWidth() >= getLeftX() &&
                object.getY() <= getBottomY() && object.getY() + object.getHeight() >= getTopY();
    }

    public boolean intersects(GameObject<?> object) {
        return object.getX() < getRightX() && object.getX() + object.getWidth() > getLeftX() &&
                object.getY() < getBottomY() && object.getY() + object.getHeight() > getTopY();
    }

}
