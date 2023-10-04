package bouncy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ball extends GameObject {

    private final double sideSpeed = 100;
    private double vSpeed = 0;

    public Ball() {
        super("images/player.png", 16, 16);
    }

}
