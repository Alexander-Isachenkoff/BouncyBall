package bouncy;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static final Map<String, Image> imagesCache = new HashMap<>();

    public static Image getImage(String filePath, String imgName) {
        String path = filePath + ":" + imgName;
        if (imagesCache.containsKey(path)) {
            return imagesCache.get(path);
        }
        Image image = FileUtils.extractImage(filePath, imgName);
        imagesCache.put(path, image);
        return image;
    }

}
