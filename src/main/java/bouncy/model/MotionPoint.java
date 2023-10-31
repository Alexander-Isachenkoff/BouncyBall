package bouncy.model;

import lombok.Getter;

public class MotionPoint extends GameObject {

    @Getter
    private final GameObject parent;

    public MotionPoint(GameObject parent) {
        this.parent = parent;
        setImagePath("images/point/point.png");
    }
}
