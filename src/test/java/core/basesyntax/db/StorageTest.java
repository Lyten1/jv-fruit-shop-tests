package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @AfterEach
    void tearDown() {
        Storage.getShopStorage().clear();
    }

    @Test
    void testUpdateWithValidData_Ok() {
        Storage.updateStorage("banana", 30);
        assertEquals(1, Storage.getShopStorage().size());
        assertEquals(30, Storage.getShopStorage().get("banana"));
    }

    @Test
    void testAddExistingKeyWithValidData_Ok() {
        Storage.updateStorage("banana", 30);
        Storage.updateStorage("banana", 20);
        assertEquals(1, Storage.getShopStorage().size());
        assertEquals(50, Storage.getShopStorage().get("banana"));
    }

    @Test
    void testAddNegativeAmount_NotOk() {
        assertThrows(RuntimeException.class,
                () -> Storage.updateStorage("banana", -30),
                "Trying to add banana with negative amount");
    }

    @Test
    void testUpdateToNegativeAmount_NotOk() {
        Storage.updateStorage("banana", 20);
        assertThrows(RuntimeException.class,
                () -> Storage.updateStorage("banana", -30),
                "Balance of fruit is negative");
    }
}
