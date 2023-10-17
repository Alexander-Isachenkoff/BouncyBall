package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public class LevelProgressData {
    @XmlAttribute
    private int index;
    @Setter
    @XmlAttribute
    private boolean done;
    @XmlAttribute
    private String fileName;
}
