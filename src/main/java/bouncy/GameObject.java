package bouncy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import lombok.Getter;

import java.io.IOException;

@Getter
public abstract class GameObject<T extends Node> {

    private final T node;

    public GameObject(String fxml) throws IOException {
        this.node = new FXMLLoader().load(Main.class.getResourceAsStream(fxml));
    }

    public GameObject(T node) {
        this.node = node;
    }

    public void addX(double x) {
        setX(node.getLayoutX() + x);
    }

    public void addY(double y) {
        setY(node.getLayoutY() + y);
    }

    public double getX() {
        return node.getLayoutX();
    }

    public void setX(double x) {
        node.setLayoutX(x);
    }

    public double getY() {
        return node.getLayoutY();
    }

    public void setY(double y) {
        node.setLayoutY(y);
    }

    public void setPosition(double x, double y) {
        node.setLayoutX(x);
        node.setLayoutY(y);
    }

    public abstract int getWidth();

    public abstract int getHeight();

}
