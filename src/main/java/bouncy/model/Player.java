package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;

@Getter
@Setter
@NoArgsConstructor
public class Player extends MovingGameObject {

    private static final double JUMP_SPEED = -300;
    private static final double SIDE_ACC = 450;
    private static final double SIDE_RESISTANCE = 100;
    private static final double MAX_SIDE_SPEED = 150;
    private static final double GRAVITY = 600;
    private static final double ROTATE_ACC = 300;
    private static final double ROTATE_RESISTANCE = 90;
    private static final double MAX_ROTATION_SPEED = 1200;

    @XmlTransient
    private double vSpeed = 0;
    @XmlTransient
    private double hSpeed = 0;
    @XmlTransient
    private double rotationSpeed = 0;
    @XmlTransient
    private boolean isDead = false;

    public Player(String imagePath, double width, double height) {
        super(imagePath, width, height);
    }

    @Override
    public void move(double seconds) {
        this.setVSpeed(this.getVSpeed() + GRAVITY * seconds);

        double res = ((getRotationSpeed() > 0) ? -ROTATE_RESISTANCE : ROTATE_RESISTANCE);
        this.setRotationSpeed(getRotationSpeed() + res * seconds);

        double res1 = ((getHSpeed() > 0) ? -SIDE_RESISTANCE : SIDE_RESISTANCE);
        this.setHSpeed(this.getHSpeed() + res1 * seconds);

        this.addAngle(this.getRotationSpeed() * seconds);
        this.addY(this.getVSpeed() * seconds);
        this.addX(this.getHSpeed() * seconds);
    }

    private double getRotationSpeedBySpeed() {
        double r = 10;
        double c = 2 * Math.PI * r;
        return getHSpeed() / c * 360;
    }

    public void jump() {
        this.setVSpeed(JUMP_SPEED);
    }

    public void moveLeft(double seconds) {
        this.setHSpeed(getHSpeed() - SIDE_ACC * seconds);
        this.setRotationSpeed(getRotationSpeed() - ROTATE_ACC * seconds);
    }

    public void moveRight(double seconds) {
        this.setHSpeed(getHSpeed() + SIDE_ACC * seconds);
        this.setRotationSpeed(getRotationSpeed() + ROTATE_ACC * seconds);
    }

    public void setHSpeed(double speed) {
        this.hSpeed = Math.max(-MAX_SIDE_SPEED, Math.min(MAX_SIDE_SPEED, speed));
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = Math.max(-MAX_ROTATION_SPEED, Math.min(MAX_ROTATION_SPEED, rotationSpeed));
    }
}
