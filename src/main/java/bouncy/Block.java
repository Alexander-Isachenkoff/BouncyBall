package bouncy;

import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Block extends GameObject<Rectangle> {

    public Block() throws IOException {
        super("block.fxml");
    }

    @Override
    public int getWidth() {
        return 30;
    }

    @Override
    public int getHeight() {
        return 30;
    }

}
