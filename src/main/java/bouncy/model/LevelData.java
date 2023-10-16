package bouncy.model;

import bouncy.FileUtils;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(name = "Level")
@XmlAccessorType(XmlAccessType.FIELD)
public class LevelData {

    @XmlElementWrapper(name = "objects")
    @XmlElements({
            @XmlElement(name = "block", type = Block.class),
            @XmlElement(name = "star", type = Star.class),
            @XmlElement(name = "player", type = Player.class),
            @XmlElement(name = "spikes", type = Spikes.class),
            @XmlElement(name = "liquid", type = Liquid.class),
            @XmlElement(name = "decor", type = Decor.class),
    })

    @Getter
    private final Set<GameObject> gameObjects = new HashSet<>();

    @Getter
    @Setter
    @XmlAttribute
    private String name;

    public static LevelData load(String fileName) {
        return FileUtils.loadXmlObject(fileName, LevelData.class);
    }

    public void add(GameObject object) {
        gameObjects.add(object);
    }

    public void remove(GameObject object) {
        gameObjects.remove(object);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> List<T> getObjects(Class<T> tClass) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getClass() == tClass)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> T getObject(Class<T> tClass) {
        return gameObjects.stream()
                .filter(gameObject -> gameObject.getClass() == tClass)
                .map(gameObject -> (T) gameObject)
                .findFirst()
                .get();
    }

    public Player getPlayer() {
        return getObject(Player.class);
    }

    public void save() {
        String fileName = name + ".xml";
        File file = new File("data/user_levels/" + fileName);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            JAXBContext context = JAXBContext.newInstance(LevelData.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(this, os);
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
