package bouncy.model;

public abstract class MovingGameObject extends GameObject {

    public MovingGameObject(String imagePath, int width, int height) {
        super(imagePath, width, height);
    }

    protected MovingGameObject(String imageFileName) {
        super(imageFileName);
    }

    public abstract void move(double seconds);

}
