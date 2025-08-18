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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    private final FileReader fileReader = new FileReaderImpl();

    @TempDir
    private Path tempDir;

    @Test
    void throwRuntimeException() {
        Path notExist = tempDir.resolve("none.csv");
        assertThrows(RuntimeException.class, () -> fileReader.read(notExist.toString()));
    }

    @Test
    void readsAllLinesWhenFileExists() throws IOException {
        Path file = tempDir.resolve("input.csv");
        List<String> expected = List.of("type,fruit,quantity", "b,banana,20", "b,apple,100");
        Files.write(file, expected, StandardOpenOption.CREATE_NEW);

        List<String> actual = fileReader.read(file.toString());

        assertEquals(expected, actual);
    }

    @Test
    void emptyFileReturnsEmptyList() throws IOException {
        Path file = tempDir.resolve("empty.csv");
        Files.createFile(file);

        List<String> actual = fileReader.read(file.toString());

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    @Test
    void nullPathThrowsNpe() {
        assertThrows(NullPointerException.class, () -> fileReader.read(null));
    }
}
