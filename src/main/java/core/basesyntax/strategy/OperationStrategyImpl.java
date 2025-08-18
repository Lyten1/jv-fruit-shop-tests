package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (!operationHandlers.containsKey(operation)) {
            throw new IllegalArgumentException("Invalid operation input");
        }
        return operationHandlers.get(operation);
    }
}
