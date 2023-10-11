package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "block")
@NoArgsConstructor
public class Block extends GameObject {
    public Block(String imagePath, double width, double height) {
        super(imagePath, width, height);
    }
}