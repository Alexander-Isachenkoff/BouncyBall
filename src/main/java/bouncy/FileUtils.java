package bouncy;

import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {

    public static List<String> getPackFileNames(String packPath) {
        List<String> fileNames = new ArrayList<>();
        try (ZipFile zipFile = new ZipFile(packPath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                fileNames.add(entry.getName());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileNames;
    }

    public static Image extractImage(String zipFilePath, String imgName) {
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.getName().equals(imgName)) {
                    return new Image(zipFile.getInputStream(entry));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<Image> extractImages(String zipPath) {
        List<Image> images = new ArrayList<>();
        try (ZipFile zipFile = new ZipFile(zipPath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                Image image = new Image(zipFile.getInputStream(entry));
                images.add(image);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return images;
    }

}
