package bouncy.model;

import bouncy.util.FileUtils;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ImageDataManager {

    private static final Map<String, ImageData> imageDataBase = new HashMap<>();

    public static ImageData getImageData(String imagePath) {
        if (imageDataBase.isEmpty()) {
            ImagesData loaded = loadImagesData();
            for (ImageData imageData : loaded.getImageData()) {
                imageDataBase.put(createKey(imageData), imageData);
            }
        }
        String key = Paths.get(imagePath).toString();
        if (imageDataBase.containsKey(key)) {
            return imageDataBase.get(key);
        } else {
            return new ImageData(key, new Collider(0, 0, 1, 1));
        }
    }

    private static String createKey(ImageData imageData) {
        return Paths.get(imageData.getImagePath()).toString();
    }

    static ImagesData loadImagesData() {
        return FileUtils.loadXmlObject("data/image_data.xml", ImagesData.class);
    }

}
