package bouncy.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Sets {
    public static <T> Set<T> of(T... items) {
        return new HashSet<>(Arrays.asList(items));
    }
}
