package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
public class Player extends MovingGameObject {

    private static final double JUMP_SPEED = -300;
    private static final double SIDE_SPEED = 100;
    private static final double GRAVITY = 600;
    private static final double ROTATE_SPEED = 210;
    @XmlTransient
    private double vSpeed = 0;
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

        this.addY(this.getVSpeed() * seconds);
    }

    public void moveLeft(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> leftBlocks = blocks.stream()
                .filter(block -> block.getY() < this.getY() && block.getY() + block.getWidth() > this.getY())
                .filter(block -> block.getColliderRightX() >= this.getX() && block.getX() < this.getX())
                .collect(Collectors.toList());
        if (leftBlocks.isEmpty()) {
            this.addX(-SIDE_SPEED * seconds);
            this.addAngle(-ROTATE_SPEED * seconds);
        }
    }

    public void moveRight(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> rightBlocks = blocks.stream()
                .filter(block -> block.getY() < this.getY() && block.getY() + block.getWidth() > this.getY())
                .filter(block -> block.getX() <= this.getColliderRightX() && block.getColliderRightX() > this.getX())
                .collect(Collectors.toList());
        if (rightBlocks.isEmpty()) {
            this.addX(SIDE_SPEED * seconds);
            this.addAngle(ROTATE_SPEED * seconds);
        }
    }

}
