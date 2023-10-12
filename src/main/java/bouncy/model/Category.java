package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

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
}
