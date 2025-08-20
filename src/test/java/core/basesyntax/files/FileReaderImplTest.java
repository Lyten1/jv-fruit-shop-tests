package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    private FileReader fileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void notExistingInputFileTest_NotOk() {
        Path notExist = tempDir.resolve("none.csv");
        assertThrows(RuntimeException.class, () -> fileReader.read(notExist.toString()));
    }

    @Test
    void readsAllLinesWhenFileExists_Ok() throws IOException {
        Path file = tempDir.resolve("input.csv");
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        Files.write(file, expected, StandardOpenOption.CREATE_NEW);

        List<String> actual = fileReader.read(file.toString());

        assertEquals(expected, actual);
    }

    @Test
    void emptyFileAsInput_Ok() throws IOException {
        Path file = tempDir.resolve("empty.csv");
        Files.createFile(file);

        List<String> actual = fileReader.read(file.toString());

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void nullPathTest_NotOk() {
        assertThrows(RuntimeException.class,
                () -> fileReader.read(null), "Not correct path to file");
    }
}
