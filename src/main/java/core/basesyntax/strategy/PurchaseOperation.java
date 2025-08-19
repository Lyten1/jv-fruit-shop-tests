package core.basesyntax.strategy;

public class PurchaseOperation implements OperationHandler {
    @Override
    public int getStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be more than 0");
        }
        return -amount;
    }
}
