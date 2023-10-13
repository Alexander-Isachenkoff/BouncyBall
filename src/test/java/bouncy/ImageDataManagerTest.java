package bouncy;

import bouncy.model.ImageData;
import bouncy.model.ImageDataManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageDataManagerTest {

    @Test
    void getCollider() {
        ImageData imageData = ImageDataManager.getImageData("images/spikes/spikes.png");
        assertEquals(0, imageData.getCollider().getX());
        assertEquals(0.5, imageData.getCollider().getY());
        assertEquals(1, imageData.getCollider().getWidth());
        assertEquals(0.5, imageData.getCollider().getHeight());
    }

}