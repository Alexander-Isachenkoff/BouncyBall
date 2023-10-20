package bouncy.model;

import bouncy.util.FileUtils;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@XmlRootElement(name = "Level")
@XmlAccessorType(XmlAccessType.FIELD)
public class LevelData {

    private static final String CUSTOM_LEVELS_DIR = "data/user_levels/";
    private static final String TEMP_FILE_NAME = "data/user_levels/temp/temp.xml";

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

    @XmlTransient
    @Setter
    private Consumer<GameObject> removeListener = gameObject -> {
    };

    public static LevelData load(String fileName) {
        return FileUtils.loadXmlObject(fileName, LevelData.class);
    }

    public static LevelData loadTemp() {
        return load(getTempFileName());
    }

    public static String getTempFileName() {
        return TEMP_FILE_NAME;
    }

    public void add(GameObject object) {
        gameObjects.add(object);
    }

    public void remove(GameObject object) {
        gameObjects.remove(object);
        removeListener.accept(object);
    }

    public void clear() {
        HashSet<GameObject> removed = new HashSet<>(gameObjects);
        gameObjects.clear();
        removed.forEach(removeListener);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> List<T> getObjects(Class<T> tClass) {
        return gameObjects.stream()
                .filter(tClass::isInstance)
                .map(gameObject -> (T) gameObject)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public <T extends GameObject> T getObject(Class<T> tClass) {
        return gameObjects.stream()
                .filter(tClass::isInstance)
                .map(gameObject -> (T) gameObject)
                .findFirst()
                .get();
    }

    public Player getPlayer() {
        return getObject(Player.class);
    }

    public void saveTemp() {
        FileUtils.saveXmlObject(this, TEMP_FILE_NAME);
    }

    public void saveCustom() {
        String fileName = name + ".xml";
        saveCustom(fileName);
    }

    private void saveCustom(String fileName) {
        FileUtils.saveXmlObject(this, CUSTOM_LEVELS_DIR + fileName);
    }

}
