package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    private static OperationStrategy strategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    @BeforeEach
    void setUp() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        strategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void mapClassesMatch() {
        assertInstanceOf(BalanceOperation.class,
                strategy.get(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(PurchaseOperation.class,
                strategy.get(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(SupplyOperation.class,
                strategy.get(FruitTransaction.Operation.SUPPLY));
        assertInstanceOf(ReturnOperation.class,
                strategy.get(FruitTransaction.Operation.RETURN));
    }

    @Test
    void nullOperationTest_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> strategy.get(null));
    }

    @Test
    void notExistingOperationTest_NotOk() {
        operationHandlers.clear();
        assertThrows(IllegalArgumentException.class,
                () -> strategy.get(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void testGettingAmountFromBalanceOperation_Ok() {
        OperationHandler operationHandler = strategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(20, operationHandler.getStock(20));
    }

    @Test
    void testGettingAmountFromSupplyOperation_Ok() {
        OperationHandler operationHandler = strategy.get(FruitTransaction.Operation.SUPPLY);
        assertEquals(20, operationHandler.getStock(20));
    }

    @Test
    void testGettingAmountFromReturnOperation_Ok() {
        OperationHandler operationHandler = strategy.get(FruitTransaction.Operation.RETURN);
        assertEquals(20, operationHandler.getStock(20));
    }

    @Test
    void testGettingAmountFromPurchaseOperation_Ok() {
        OperationHandler operationHandler = strategy.get(FruitTransaction.Operation.PURCHASE);
        assertEquals(-20, operationHandler.getStock(20));
    }
}
