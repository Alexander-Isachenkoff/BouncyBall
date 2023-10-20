package bouncy.ui;

import javafx.scene.control.Button;

class BigButton extends Button {
    public BigButton(String text, Runnable onAction) {
        super(text);
        this.getStyleClass().add("big-button");
        this.setOnAction(event -> onAction.run());
    }
}
