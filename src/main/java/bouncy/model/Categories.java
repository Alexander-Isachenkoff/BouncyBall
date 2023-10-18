package bouncy.model;

import bouncy.util.FileUtils;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
public class Categories {
    @XmlElement(name = "category")
    private List<Category> categories;

    public static List<Category> load() {
        return FileUtils.loadXmlObject("data/categories.xml", Categories.class).categories;
    }
}
