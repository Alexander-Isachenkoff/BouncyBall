package bouncy.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoriesTest {

    @Test
    void load() {
        List<Category> categories = Categories.load();
        assertEquals(4, categories.size());
    }

}