package bouncy.ui;

import javafx.scene.control.Button;

class MiniButton extends Button {
    public MiniButton(String text, Runnable onAction) {
        super(text);
        this.getStyleClass().add("mini-button");
        this.setOnAction(event -> onAction.run());
    }
}
