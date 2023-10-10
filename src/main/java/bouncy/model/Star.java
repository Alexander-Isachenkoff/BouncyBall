package bouncy.model;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@XmlRootElement
public class Star extends GameObject {

    public Star(int width, int height) {
        super("images/other", "star.png", width, height);
    }

}
