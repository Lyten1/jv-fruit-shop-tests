package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private static ShopService shopService;
    private static OperationStrategy operationStrategy;
    private static List<FruitTransaction> fruitTransactionsList;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);

        fruitTransactionsList = new ArrayList<>();
        fruitTransactionsList.add(createFruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 20));
        fruitTransactionsList.add(createFruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100));
        fruitTransactionsList.add(createFruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 100));
        fruitTransactionsList.add(createFruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 13));
        fruitTransactionsList.add(createFruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 70));
    }

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.getShopStorage().clear();
    }

    @Test
    void processTransactionsWithValidData_Ok() {
        shopService.process(fruitTransactionsList);
        int bananasExpected = 107;
        int applesExpected = 30;
        assertEquals(bananasExpected, Storage.getShopStorage().get("banana"));
        assertEquals(applesExpected, Storage.getShopStorage().get("apple"));
    }

    private static FruitTransaction createFruitTransaction(
            FruitTransaction.Operation operation, String fruit, int quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setQuantity(quantity);
        fruitTransaction.setFruit(fruit);
        return fruitTransaction;
    }

}
