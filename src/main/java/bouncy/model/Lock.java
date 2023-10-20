package bouncy.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAttribute;

public class Lock extends Block {
    @Getter
    @Setter
    @XmlAttribute
    private LockType lockType;
}
