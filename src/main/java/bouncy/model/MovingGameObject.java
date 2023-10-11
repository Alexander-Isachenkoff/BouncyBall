package bouncy.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class MovingGameObject extends GameObject {

    public MovingGameObject(String imagePath, double width, double height) {
        super(imagePath, width, height);
    }

    public abstract void move(double seconds);

}
