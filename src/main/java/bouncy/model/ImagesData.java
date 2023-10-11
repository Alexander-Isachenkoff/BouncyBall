package bouncy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ImagesData {
    @XmlElementWrapper(name = "data")
    @XmlElement(name = "imageData")
    private Set<ImageData> imageData;
}
