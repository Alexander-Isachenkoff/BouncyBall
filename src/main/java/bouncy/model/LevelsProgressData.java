package bouncy.model;

import bouncy.FileUtils;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
public class LevelsProgressData {
    @XmlElement(name = "levelProgressData")
    private List<LevelProgressData> levelProgressData;

    public static List<LevelProgressData> load() {
        return FileUtils.loadXmlObject("data/levels_data.xml", LevelsProgressData.class).levelProgressData;
    }
}
