package bouncy;

import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static final Map<String, Image> imagesCache = new HashMap<>();

    public static Image getImage(String imagePath) {
        File file = new File(imagePath);
        if (imagesCache.containsKey(imagePath)) {
            return imagesCache.get(imagePath);
        }
        Image image = FileUtils.extractImage(file.getParent(), file.getName());
        imagesCache.put(imagePath, image);
        return image;
    }

}
