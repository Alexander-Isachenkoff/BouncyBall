package bouncy.model;

public abstract class MovingGameObject extends GameObject {

    public MovingGameObject(String imagePack, String imageName, int width, int height) {
        super(imagePack, imageName, width, height);
    }

    public abstract void move(double seconds);

}
