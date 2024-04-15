import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeneratorServiceTest {

    @DisplayName("Проверка загрузки файла, превышающего размер 1.5 мб, генерация исключения")
    @Test
    void testThrowException() {
        File testFile = new File("wrong_size.pdf");
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.getTextFromFile(any())).thenThrow(new IllegalArgumentException("File size exceeded!"));
        assertThrows(IllegalArgumentException.class, () -> generatorService.getTextFromFile(testFile));
    }

    @DisplayName("Проверка загрузки файла, не превышающего размер 1.5 мб")
    @Test
    void testGetTextFromFile() {
        File testFile = new File("ok_size.pdf");
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.getTextFromFile(any())).thenReturn("Text file with lecture");

        assertDoesNotThrow(() -> generatorService.getTextFromFile(testFile));
        assertEquals("Text file with lecture", generatorService.getTextFromFile(testFile));
    }

    @DisplayName("Проверка загрузки файла, не превышающего 6000 символов")
    @Test
    void testGetTextFromFileValidLength() {
        File testFile = new File("ok_length.pdf");
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.getTextFromFile(any())).thenReturn("Text file with lecture");

        assertDoesNotThrow(() -> generatorService.getTextFromFile(testFile));
        assertEquals("Text file with lecture", generatorService.getTextFromFile(testFile));
    }

    @DisplayName("Проверка загрузки файла, превышающего 6000 символов, ошибка загрузки")
    @Test
    void testGetTextFromFileNotValidLength() {
        File testFile = new File("wrong_length.pdf");
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.getTextFromFile(any())).thenThrow(new IllegalArgumentException("File length exceeded!"));
        assertThrows(IllegalArgumentException.class, () -> generatorService.getTextFromFile(testFile));
    }

    @DisplayName("Проверка файла с неверным расширением")
    @ParameterizedTest
    @ValueSource(strings = {"wrong_extension.jpeg", "wrong_extension.png", "wrong_extension.txtt", "wrong_extension.ppdf"})
    void testWrongExtensionThrowException(String fileName) {
        File testFile = new File(fileName);
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.getTextFromFile(any())).thenThrow(new IllegalArgumentException("Wrong file extension!"));
        assertThrows(IllegalArgumentException.class, () -> generatorService.getTextFromFile(testFile));
    }

    @DisplayName("Проверка файла с верным расширением")
    @ParameterizedTest
    @ValueSource(strings = {"ok_extension.doc", "ok_extension.pdf", "ok_extension.txt"})
    void testOkExtensionWithoutException(String fileName) {
        File testFile = new File(fileName);
        GeneratorService generatorService = mock(GeneratorService.class);
        assertDoesNotThrow(() -> generatorService.getTextFromFile(testFile));
    }

    @DisplayName("Проверка парсинга неполного файла")
    @Test
    void testIsFileFullWrong() {
        File testFile = new File("not_valid_file.pdf");
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.isFileFull(any())).thenReturn(false);
        assertFalse(generatorService.isFileFull(testFile));
    }

    @DisplayName("Проверка парсинга полного файла")
    @Test
    void testIsFileFull() {
        File testFile = new File("valid_file.pdf");
        GeneratorService generatorService = mock(GeneratorService.class);
        when(generatorService.isFileFull(any())).thenReturn(true);
        assertTrue(generatorService.isFileFull(testFile));
    }
}
