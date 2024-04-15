import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ParserTextServiceTest {
    String text = """
        1. Кто впервые с помощью магнитного поля получил электри­ческий ток?
                    
        1) Ш. Кулон
        2) А. Ампер
        3) М. Фарадей
        4) Н. Тесла
                    
        2. Как называется явление возникновения электрического тока в замкнутом контуре при изменении магнитного пото­ка через контур?
                    
        1) Намагничивание
        2) Электролиз
        3) Электромагнитная индукция
        4) Резонанс
        """;

    String errorText = """
        Не получилось сгенерировать текст по вашему запросу, попробуйте еще раз позже!
        """;

    @DisplayName("Проверка парсинга от GPT yandex")
    @Test
    void shouldReturnTextModelYandex() {
        ParserTextService parserTextService = mock(ParserTextService.class);
        TestModel testModel = new TestModel();
        when(parserTextService.parseFromGptYandex(text)).thenReturn(testModel);
        assertEquals(parserTextService.parseFromGptYandex(text), testModel);
    }

    @DisplayName("Проверка парсинга от GPT yandex, генерация исключения")
    @Test
    void shouldThrowExceptionWhenParseYandex() {
        ParserTextService parserTextService = mock(ParserTextService.class);
        when(parserTextService.parseFromGptYandex(errorText)).thenThrow(new RuntimeException("Cannot parse text, please try again!"));
        assertThrows(RuntimeException.class, () -> parserTextService.parseFromGptYandex(errorText));
    }

    @DisplayName("Тестирование соответствия текста промту")
    @Test
    void createPromptForGpt(){
        ParserTextService parserTextService = mock(ParserTextService.class);
        String result = "Prompt";
        when(parserTextService.createPrompt(0.1, "", "")).thenReturn(result);
        assertEquals(parserTextService.createPrompt(0.1, "", ""), result);
    }
}
