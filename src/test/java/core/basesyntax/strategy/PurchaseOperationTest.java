package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    private static OperationHandler handler = new PurchaseOperation();

    @Test
    void getStockTestWithPositiveNumber() {
        assertEquals(-7, handler.getStock(7));
    }

    @Test
    void getStockWithZero() {
        assertThrows(IllegalArgumentException.class, () -> handler.getStock(0));
    }

    @Test
    void getStock_negativeInput_policy() {
        assertThrows(IllegalArgumentException.class, () -> handler.getStock(-3));
    }
}
