package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
public class Category {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String className;
    @XmlAttribute
    private String imagePath;
    @XmlAttribute
    private String pack;
    @XmlElement(name = "category")
    private final List<Category> categories = new ArrayList<>();

    @XmlElementWrapper(name = "objects")
    @XmlElements({
            @XmlElement(name = "block", type = Block.class),
            @XmlElement(name = "star", type = Star.class),
            @XmlElement(name = "player", type = Player.class),
            @XmlElement(name = "spikes", type = Spikes.class),
            @XmlElement(name = "liquid", type = Liquid.class),
            @XmlElement(name = "decor", type = Decor.class),
            @XmlElement(name = "key", type = Key.class),
            @XmlElement(name = "lock", type = Lock.class),
    })

    @Getter
    private final Set<GameObject> gameObjects = new HashSet<>();
}
