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
    @XmlAttribute
    private double colliderX;
    @XmlAttribute
    private double colliderY;
    @XmlAttribute
    private double colliderWidth;
    @XmlAttribute
    private double colliderHeight;
    @XmlAttribute
    private String imagePath;

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

    protected GameObject(String imagePath) {
        this(imagePath, 30, 30);
    }

    protected GameObject(String imagePath, int width, int height) {
        setWidth(width);
        setHeight(height);
        setImagePath(imagePath);
    }

    public void addX(double x) {
        setX(getX() + x);
    }

    public void addY(double y) {
        setY(getY() + y);
    }

    public void setPosition(double x, double y) {
        setX(x);
        setY(y);
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

    public double getRightX() {
        return getX() + getWidth();
    }

    public double getBottomY() {
        return getY() + getHeight();
    }

    public boolean intersects(GameObject object) {
        return object.getX() < getRightX() && object.getX() + object.getWidth() > getX() &&
                object.getY() < getBottomY() && object.getY() + object.getHeight() > getY();
    }

}
