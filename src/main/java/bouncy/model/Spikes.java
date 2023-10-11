package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "spikes")
@NoArgsConstructor
public class Spikes extends GameObject {

    public Spikes(String imagePath, double width, double height) {
        super(imagePath, width, height);
    }
}
