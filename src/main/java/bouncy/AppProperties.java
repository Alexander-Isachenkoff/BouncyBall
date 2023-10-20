package bouncy;

import javafx.beans.property.SimpleBooleanProperty;

public class AppProperties {
    private static final SimpleBooleanProperty collidersProperty = new SimpleBooleanProperty(false);

    public synchronized static SimpleBooleanProperty getCollidersProperty() {
        return collidersProperty;
    }
}
