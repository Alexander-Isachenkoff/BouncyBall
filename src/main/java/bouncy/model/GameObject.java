package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.function.Consumer;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public abstract class GameObject {

    @XmlAttribute
    private double x;
    @XmlAttribute
    private double y;
    @XmlAttribute
    private double width;
    @XmlAttribute
    private double height;
    @XmlTransient
    private double angle;

    @XmlTransient
    private ImageData imageData;

    @XmlTransient
    private Consumer<Double> onXChanged = d -> {
    };
    @XmlTransient
    private Consumer<Double> onYChanged = d -> {
    };
    @XmlTransient
    private Consumer<Double> onWidthChanged = d -> {
    };
    @XmlTransient
    private Consumer<Double> onHeightChanged = d -> {
    };
    @XmlTransient
    private Consumer<Double> onAngleChanged = d -> {
    };

    @XmlTransient
    @Getter
    private LevelData levelData;

    @XmlAttribute
    public String getImagePath() {
        return imageData.getImagePath();
    }

    public void setImagePath(String imagePath) {
        imageData = ImageDataManager.getImageData(imagePath);
    }

    public void addX(double x) {
        setX(getX() + x);
    }

    public void addY(double y) {
        setY(getY() + y);
    }

    public void setX(double x) {
        this.x = x;
        onXChanged.accept(x);
    }

    public void setY(double y) {
        this.y = y;
        onYChanged.accept(y);
    }

    public void setWidth(double width) {
        this.width = width;
        onWidthChanged.accept(width);
    }

    public void setHeight(double height) {
        this.height = height;
        onHeightChanged.accept(height);
    }

    public void setAngle(double angle) {
        this.angle = angle % 360;
        onAngleChanged.accept(this.angle);
    }

    public void addAngle(double angle) {
        setAngle(getAngle() + angle);
    }

    public double getColliderAbsoluteX() {
        return getX() + getColliderX();
    }

    public double getColliderX() {
        return getImageData().getCollider().getX() * getWidth();
    }

    public double getColliderAbsoluteY() {
        return getY() + getColliderY();
    }

    public double getColliderY() {
        return getImageData().getCollider().getY() * getHeight();
    }

    public double getColliderRightX() {
        return getColliderAbsoluteX() + getColliderWidth();
    }

    public double getColliderWidth() {
        return getImageData().getCollider().getWidth() * getWidth();
    }

    public double getColliderBottomY() {
        return getColliderAbsoluteY() + getColliderHeight();
    }

    public double getColliderHeight() {
        return getImageData().getCollider().getHeight() * getHeight();
    }

    public boolean intersects(GameObject object) {
        return object.getColliderAbsoluteX() < getColliderRightX() && object.getColliderRightX() > getColliderAbsoluteX() &&
                object.getColliderAbsoluteY() < getColliderBottomY() && object.getColliderBottomY() > getColliderAbsoluteY();
    }

    public void affectPlayer(Player player) {
    }

}
