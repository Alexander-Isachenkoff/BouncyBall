package bouncy.model;

import bouncy.FileUtils;
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

    public static final String USER_LEVELS_DIR = "data/user_levels/";
    public static final String TEMP_FILE_NAME = "temp.xml";

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
        return load(USER_LEVELS_DIR + TEMP_FILE_NAME);
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

    public void saveTemp() {
        save(TEMP_FILE_NAME);
    }

    public void save() {
        String fileName = name + ".xml";
        save(fileName);
    }

    private void save(String fileName) {
        FileUtils.saveXmlObject(this, USER_LEVELS_DIR + fileName);
    }

}
