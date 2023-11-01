package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@NoArgsConstructor
public class MovingGameObject extends GameObject {

    @XmlAttribute
    @Getter
    private final int moveSpeed = 100;
    @Setter
    @Getter
    private MotionPath motionPath;
    @XmlTransient
    private boolean forwardMotion = true;

    public void move(double seconds) {
        if (getMotionPath() != null) {
            if (forwardMotion) {
                double dx = getMotionPath().getEndX() - getX();
                double dy = getMotionPath().getEndY() - getY();
                double dist = Math.sqrt(dx * dx + dy * dy);
                addX(moveSpeed * seconds * dx / dist);
                addY(moveSpeed * seconds * dy / dist);
                if (dx > 0 && getX() > getMotionPath().getEndX()) {
                    forwardMotion = false;
                }
                if (dx < 0 && getX() < getMotionPath().getEndX()) {
                    forwardMotion = false;
                }
                if (dy > 0 && getY() > getMotionPath().getEndY()) {
                    forwardMotion = false;
                }
                if (dy < 0 && getY() < getMotionPath().getEndY()) {
                    forwardMotion = false;
                }
            } else {
                double dx = getMotionPath().getStartX() - getX();
                double dy = getMotionPath().getStartY() - getY();
                double dist = Math.sqrt(dx * dx + dy * dy);
                addX(moveSpeed * seconds * dx / dist);
                addY(moveSpeed * seconds * dy / dist);
                if (dx > 0 && getX() > getMotionPath().getStartX()) {
                    forwardMotion = true;
                }
                if (dx < 0 && getX() < getMotionPath().getStartX()) {
                    forwardMotion = true;
                }
                if (dy > 0 && getY() > getMotionPath().getStartY()) {
                    forwardMotion = true;
                }
                if (dy < 0 && getY() < getMotionPath().getStartY()) {
                    forwardMotion = true;
                }
            }
        }
    }

}
