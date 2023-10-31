package bouncy.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class MotionPath {
    @XmlAttribute
    private double startX;
    @XmlAttribute
    private double startY;
    @XmlAttribute
    private double endX;
    @XmlAttribute
    private double endY;
}
