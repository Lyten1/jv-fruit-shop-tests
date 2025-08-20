package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getShopStorage().clear();
    }

    @Test
    void emptyInputTest_Ok() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void validInputTest_Ok() {
        Storage.getShopStorage().put("banana", 20);
        Storage.getShopStorage().put("apple", 100);
        String expected = "fruit,quantity\n"
                + "banana,20\n"
                + "apple,100";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
