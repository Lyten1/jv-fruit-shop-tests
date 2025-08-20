package core.basesyntax.files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    private FileWriter fileWriter;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void nullPathTest_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> fileWriter.write("text", null));
    }

    @Test
    void writeToFileWithCorrectData_Ok() throws IOException {
        Path file = tempDir.resolve("out.txt");
        String text = "hello, world";

        fileWriter.write(text, file.toString());

        assertTrue(Files.exists(file));
        assertEquals(text, new String(Files.readAllBytes(file)));
    }

    @Test
    void overwritesExistingFileTest_Ok() throws IOException {
        Path file = tempDir.resolve("out.txt");
        Files.write(file, List.of("old"));
        String text = "new content";

        fileWriter.write(text, file.toString());

        assertEquals(text, new String(Files.readAllBytes(file)));
    }

    @Test
    void writingToDirectory_NotOk() throws IOException {
        Path dir = tempDir.resolve("aDir");
        Files.createDirectories(dir);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> fileWriter.write("x", dir.toString()));

        assertTrue(ex.getMessage().startsWith("Can't write to file: " + dir));
        assertNotNull(ex.getCause());
        assertTrue(ex.getCause() instanceof IOException);
    }

}
