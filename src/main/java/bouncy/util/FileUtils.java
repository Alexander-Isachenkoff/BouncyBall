package bouncy.util;

import javafx.scene.image.Image;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @SuppressWarnings("unchecked")
    public static <T> T loadXmlObject(String filePath, Class<T> tClass) {
        try (InputStream ois = Files.newInputStream(Paths.get(filePath))) {
            JAXBContext context = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(ois);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveXmlObject(Object object, String fileName) {
        File file = new File(fileName);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(object, os);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
