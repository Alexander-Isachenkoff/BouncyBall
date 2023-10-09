package bouncy.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(name = "block")
public class Block extends GameObject {

    public Block() {
        super("images/grass.png");
    }

}
