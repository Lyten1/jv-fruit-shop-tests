package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.getShopStorage().clear();
    }

    @Test
    void testUpdateWithValidData() {
        Storage.updateStorage("banana", 30);
        assertEquals(1, Storage.getShopStorage().size());
        assertEquals(30, Storage.getShopStorage().get("banana"));
    }

    @Test
    void testAddExistingKeyWithValidData() {
        Storage.updateStorage("banana", 30);
        Storage.updateStorage("banana", 20);
        assertEquals(1, Storage.getShopStorage().size());
        assertEquals(50, Storage.getShopStorage().get("banana"));
    }

    @Test
    void testAddNegativeAmount() {
        assertThrows(RuntimeException.class,
                () -> Storage.updateStorage("banana", -30),
                "Trying to add banana with negative amount");
    }

    @Test
    void testUpdateToNegativeAmount() {
        Storage.updateStorage("banana", 20);
        assertThrows(RuntimeException.class,
                () -> Storage.updateStorage("banana", -30),
                "Balance of fruit is negative");
    }
}
