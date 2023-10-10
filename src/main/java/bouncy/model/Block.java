package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "block")
@NoArgsConstructor
public class Block extends GameObject {

    public Block(String imagePack, String imageName, int width, int height) {
        super(imagePack, imageName, width, height);
    }

    public Block(String imagePack, String imageName) {
        super(imagePack, imageName);
    }

}
