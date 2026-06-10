package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    void main_PassOnlyGame_DisplaysWinner() {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        Locale originalLocale = Locale.getDefault();
        String input = "2\nSophie\nJordan\n" + "0\n".repeat(100);
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            Locale.setDefault(Locale.ENGLISH);
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            Locale.setDefault(originalLocale);
        }

        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Game over!"));
    }
}
