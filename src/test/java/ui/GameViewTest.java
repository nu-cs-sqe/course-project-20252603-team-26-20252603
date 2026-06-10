package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import domain.game.Card;
import domain.game.CardType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameViewTest {
    private ByteArrayOutputStream output;
    private PrintStream originalOut;

    // ── helpers ────────────────────────────────────────────────────────────────

    @BeforeEach
    void redirectStdOut() {
        output = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restoreStdOut() {
        System.setOut(originalOut);
    }

    /** Returns captured output as a UTF-8 string. */
    private String captured() {
        return output.toString(StandardCharsets.UTF_8);
    }

    /**
     * Creates a GameView whose stdin is backed by the given lines joined with newlines.
     * Used for promptPlayerNames tests that need simulated keyboard input.
     */
    private GameView viewWithInput(String... lines) {
        String input = String.join("\n", lines) + "\n";
        InputStream fakeIn = new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8));
        return new GameView(fakeIn);
    }

    @Test
    void displayGameOver_WithWinnerName_ShowsGameOverAndWinner() {
        GameView view = new GameView();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            view.displayGameOver("Jordan");
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Game over! Jordan wins!"));
    }

    @Test
    void displayHand_WithCards_ShowsPlayerNameAndIndexedCards() {
        GameView view = new GameView();
        List<Card> cards = List.of(new Card(CardType.SKIP), new Card(CardType.BEARD_CAT));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

            view.displayHand("Sophie", cards);
        } finally {
            System.setOut(originalOut);
        }

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Sophie, your hand:"));
        assertTrue(text.contains("1. SKIP"));
        assertTrue(text.contains("2. BEARD_CAT"));
    }


    @Test
    void displayStartScreen_DisplayOnce_ShowsTitle() {
        GameView view = new GameView();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            view.displayStartScreen();
        } finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Exploding Kittens"));
    }

    @Test
    void promptPlayerNames_ZeroPlayers_ReturnsEmptyList() {
        GameView view = viewWithInput("0");

        List<String> names = view.promptPlayerNames();

        assertTrue(names.isEmpty());
    }

}
