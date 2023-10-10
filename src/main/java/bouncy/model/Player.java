package bouncy.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
@Getter
@Setter
public class Player extends MovingGameObject {

    private static final double JUMP_SPEED = -300;
    private static final double SIDE_SPEED = 100;
    private static final double GRAVITY = 600;
    @XmlAttribute
    private double vSpeed = 0;
    @XmlTransient
    private LevelData levelData;

    public Player() {
        this(16, 16);
    }

    public Player(int width, int height) {
        super("images/other", "player.png", width, height);
    }

    @Override
    public void move(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> bottomBlocks = blocks.stream()
                .filter(block -> block.getX() <= this.getRightX() && block.getX() + block.getWidth() >= this.getX())
                .filter(block -> block.getY() <= this.getBottomY() && block.getBottomY() > this.getY())
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
                .filter(block -> block.getRightX() >= this.getX() && block.getX() < this.getX())
                .collect(Collectors.toList());
        if (leftBlocks.isEmpty()) {
            this.addX(-SIDE_SPEED * seconds);
        }
    }

    public void moveRight(double seconds) {
        List<Block> blocks = levelData.getObjects(Block.class);

        List<Block> rightBlocks = blocks.stream()
                .filter(block -> block.getY() < this.getY() && block.getY() + block.getWidth() > this.getY())
                .filter(block -> block.getX() <= this.getRightX() && block.getRightX() > this.getX())
                .collect(Collectors.toList());
        if (rightBlocks.isEmpty()) {
            this.addX(SIDE_SPEED * seconds);
        }
    }

}
