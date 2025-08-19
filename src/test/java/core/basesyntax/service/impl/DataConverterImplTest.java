package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private static ArrayList<String> inputList = new ArrayList<>();
    private static DataConverterImpl dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
        inputList.clear();
        inputList.add("type,fruit,quantity");
        inputList.add("b,banana,20");
        inputList.add("b,apple,100");
    }

    @Test
    void validData() {
        FruitTransaction one = new FruitTransaction();
        one.setFruit("banana");
        one.setQuantity(20);
        one.setOperation(FruitTransaction.Operation.BALANCE);

        FruitTransaction two = new FruitTransaction();
        two.setFruit("apple");
        two.setQuantity(100);
        two.setOperation(FruitTransaction.Operation.BALANCE);

        List<FruitTransaction> expected = new ArrayList<>();
        expected.add(one);
        expected.add(two);
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputList);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void nullListOk() {
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(null),
                "list of input data must not be null");
    }

    @Test
    void noTransactionOnlyHeader() {
        List<FruitTransaction> expected = new ArrayList<>();
        inputList.remove(2);
        inputList.remove(1);
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputList);
        assertEquals(expected, actual);
    }

    @Test
    void emptyList() {
        List<FruitTransaction> expected = new ArrayList<>();
        inputList.clear();
        List<FruitTransaction> actual = dataConverter.convertToTransaction(inputList);
        assertEquals(expected, actual);
    }

    @Test
    void moreThanThreeParametersList() {
        inputList.add("b,baraban,34,23");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputList),
                "Input file data in wrong format, every row must have 3 parameters");
    }

    @Test
    void negativeQuantityTestList() {
        inputList.add("b,baraban,-34");
        assertThrows(RuntimeException.class,
                () -> dataConverter.convertToTransaction(inputList),
                "Negative quantity not allowed: b,baraban,-34");
    }

    @Test
    void invalidOperatorTest() {
        inputList.add("e,baraban,34");
        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(inputList),
                "Unknown code: e");
    }
}
