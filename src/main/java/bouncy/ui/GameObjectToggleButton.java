package bouncy.ui;

import bouncy.model.GameObject;
import javafx.scene.control.ToggleButton;
import lombok.Getter;

@Getter
public class GameObjectToggleButton extends ToggleButton {

    private final GameObject gameObject;

    public GameObjectToggleButton(GameObject gameObject) {
        this.gameObject = gameObject;
        setGraphic(new GameObjectNode(gameObject));
        getStyleClass().add("gameObjectToggleButton");
    }

}
