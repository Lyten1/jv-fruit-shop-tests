package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    private static OperationHandler handler = new PurchaseOperation();

    @Test
    void getStockTestWithPositiveNumber_Ok() {
        assertEquals(-7, handler.getStock(7));
    }

    @Test
    void getStockWithZero_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> handler.getStock(0));
    }

    @Test
    void getStockWithNegativeInput_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> handler.getStock(-3));
    }
}
