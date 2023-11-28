package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "block")
@NoArgsConstructor
public class Block extends MovingGameObject {

    @Override
    public void affectPlayer(Player player) {
        if (intersects(player)) {
            IntersectionSide intersection = findIntersectionSide(player);
            switch (intersection) {
                case TOP:
                    player.setY(getColliderAbsoluteY() - player.getHeight() + player.getColliderY());
                    player.jump();
                    break;
                case RIGHT:
                    player.setX(getColliderRightX() - player.getColliderX());
                    player.setHSpeed(-player.getHSpeed());
                    player.setRotationSpeed(-player.getRotationSpeed());
                    break;
                case BOTTOM:
                    player.setY(getColliderBottomY() - player.getColliderY());
                    player.setVSpeed(-player.getVSpeed() / 2);
                    break;
                case LEFT:
                    player.setX(getColliderAbsoluteX() - player.getWidth() + player.getColliderX());
                    player.setHSpeed(-player.getHSpeed());
                    player.setRotationSpeed(-player.getRotationSpeed());
                    break;
            }
        }
    }

    private IntersectionSide findIntersectionSide(GameObject object) {
        double minX = Math.max(getColliderAbsoluteX(), object.getColliderAbsoluteX());
        double maxX = Math.min(getColliderRightX(), object.getColliderRightX());
        double minY = Math.max(getColliderAbsoluteY(), object.getColliderAbsoluteY());
        double maxY = Math.min(getColliderBottomY(), object.getColliderBottomY());
        double w = maxX - minX;
        double h = maxY - minY;

        if (w > h) {
            if (minY == getColliderAbsoluteY()) {
                return IntersectionSide.TOP;
            } else {
                return IntersectionSide.BOTTOM;
            }
        } else {
            if (minX == getColliderAbsoluteX()) {
                return IntersectionSide.LEFT;
            } else {
                return IntersectionSide.RIGHT;
            }
        }
    }

    private enum IntersectionSide {
        TOP, RIGHT, BOTTOM, LEFT
    }

}
