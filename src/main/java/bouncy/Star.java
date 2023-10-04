package bouncy;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Star extends GameObject<ImageView> {

    public Star() {
        super(new ImageView());
        ImageView imageView = getNode();
        imageView.setImage(new Image(Main.class.getResourceAsStream("images/star.png")));
        imageView.setFitWidth(getWidth());
        imageView.setFitHeight(getHeight());
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
