package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
import java.nio.file.Paths;

@NoArgsConstructor
@XmlRootElement
public class Star extends GameObject {

    public Star(double width, double height) {
        super(Paths.get("images/other", "star.png").toString(), width, height);
    }

}
