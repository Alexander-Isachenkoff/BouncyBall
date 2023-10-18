package bouncy.model;

import bouncy.util.FileUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
public class LevelsProgressData {

    private static final String LEVELS_PROGRESS_FILENAME = "data/levels_data.xml";

    @Getter
    @XmlElement(name = "levelProgressData")
    private List<LevelProgressData> levelProgressData;

    public static LevelsProgressData load() {
        return FileUtils.loadXmlObject(LEVELS_PROGRESS_FILENAME, LevelsProgressData.class);
    }

    public void setDone(int index, boolean done) {
        get(index).setDone(done);
    }

    public LevelProgressData get(int index) {
        return this.getLevelProgressData().stream()
                .filter(data -> data.getIndex() == index)
                .findFirst()
                .get();
    }

    public void save() {
        FileUtils.saveXmlObject(this, LEVELS_PROGRESS_FILENAME);
    }

}
