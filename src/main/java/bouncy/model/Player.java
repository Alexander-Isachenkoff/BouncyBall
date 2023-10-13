package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class Player extends MovingGameObject {

    private static final double JUMP_SPEED = -300;
    private static final double SIDE_ACC = 450;
    private static final double SIDE_RESISTANCE = 100;
    private static final double MAX_SIDE_SPEED = 150;
    private static final double GRAVITY = 600;
    private static final double ROTATE_ACC = 200;
    private static final double ROTATE_RESISTANCE = 45;
    @XmlTransient
    private double vSpeed = 0;
    @XmlTransient
    private double hSpeed = 0;
    @XmlTransient
    private double rotationSpeed = 0;
    @XmlTransient
    private LevelData levelData;

    public Player(String imagePath, double width, double height) {
        super(imagePath, width, height);
    }

    @Override
    public void move(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> bottomBlocks = blocks.stream()
                .filter(block -> block.getX() <= this.getColliderRightX() && block.getX() + block.getWidth() >= this.getColliderAbsoluteX())
                .filter(block -> block.getY() <= this.getColliderBottomY() && block.getColliderBottomY() > this.getColliderAbsoluteY())
                .collect(Collectors.toList());

        if (!bottomBlocks.isEmpty() && this.getVSpeed() >= 0) {
            this.setVSpeed(JUMP_SPEED);
        }

        this.setVSpeed(this.getVSpeed() + GRAVITY * seconds);

        double res = ((getRotationSpeed() > 0) ? -ROTATE_RESISTANCE : ROTATE_RESISTANCE);
        this.setRotationSpeed(getRotationSpeed() + res * seconds);

        double res1 = ((getHSpeed() > 0) ? -SIDE_RESISTANCE : SIDE_RESISTANCE);
        this.setHSpeed(this.getHSpeed() + res1 * seconds);

        this.addAngle(this.getRotationSpeed() * seconds);
        this.addY(this.getVSpeed() * seconds);
        this.addX(this.getHSpeed() * seconds);
    }

    public void moveLeft(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> leftBlocks = blocks.stream()
                .filter(block -> block.getY() < this.getY() && block.getY() + block.getWidth() > this.getY())
                .filter(block -> block.getColliderRightX() >= this.getX() && block.getX() < this.getX())
                .collect(Collectors.toList());
        if (leftBlocks.isEmpty()) {
            this.setHSpeed(getHSpeed() - SIDE_ACC * seconds);
            this.setRotationSpeed(getRotationSpeed() - ROTATE_ACC * seconds);
        }
    }

    public void moveRight(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> rightBlocks = blocks.stream()
                .filter(block -> block.getY() < this.getY() && block.getY() + block.getWidth() > this.getY())
                .filter(block -> block.getX() <= this.getColliderRightX() && block.getColliderRightX() > this.getX())
                .collect(Collectors.toList());
        if (rightBlocks.isEmpty()) {
            this.setHSpeed(getHSpeed() + SIDE_ACC * seconds);
            this.setRotationSpeed(getRotationSpeed() + ROTATE_ACC * seconds);
        }
    }

    public void setHSpeed(double hSpeed) {
        this.hSpeed = Math.max(-MAX_SIDE_SPEED, Math.min(MAX_SIDE_SPEED, hSpeed));
    }
}
