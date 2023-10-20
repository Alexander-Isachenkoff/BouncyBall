package bouncy;

import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;

public class AppProperties {
    @Getter
    private static final SimpleBooleanProperty collidersProperty = new SimpleBooleanProperty(false);
}
