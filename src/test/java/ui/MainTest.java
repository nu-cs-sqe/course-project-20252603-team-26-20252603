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

        StringBuilder inputBuilder = new StringBuilder();

        inputBuilder.append("1\n");

        inputBuilder.append("2\n");
        inputBuilder.append("Fae\n");
        inputBuilder.append("Jordan\n");

        for (int i = 0; i < 100; i++) {
            inputBuilder.append("1\n");
            inputBuilder.append("0\n");
        }

        inputBuilder.append("0\n".repeat(200));

        String input = inputBuilder.toString();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            Locale.setDefault(Locale.ENGLISH);
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            Main.main(new String[0]);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            Locale.setDefault(originalLocale);
        }

        String outputString = output.toString(StandardCharsets.UTF_8);
        assertTrue(outputString.contains("Game over") ||
                outputString.contains("winner") ||
                outputString.contains("Wins"));
    }
}
