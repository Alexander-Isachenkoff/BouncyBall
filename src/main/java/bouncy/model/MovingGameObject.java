package bouncy.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class MovingGameObject extends GameObject {

    public abstract void move(double seconds);

}
