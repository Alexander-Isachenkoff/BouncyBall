package bouncy.model;

import bouncy.FileUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlockFamily {
    private String name;
    private String imagesPack;
    private List<String> imageNames;

    public BlockFamily(String name, String imagesPack) {
        this.name = name;
        this.imagesPack = imagesPack;
        this.imageNames = FileUtils.getPackFileNames(imagesPack);
    }
}
