package bouncy.model;

import lombok.Getter;

public class MotionPoint extends GameObject {

    @Getter
    private final MovingGameObject parent;

    public MotionPoint(MovingGameObject parent) {
        this.parent = parent;
        setImagePath("images/point/point.png");
    }
}
