package ui;

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

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void promptPlayerNames_TwoPlayers_ReturnsBothNamesInOrder() {
        GameView view = viewWithInput("2", "Alice", "Bob");

        List<String> names = view.promptPlayerNames();

        assertEquals(2, names.size());
        assertEquals("Alice", names.get(0));
        assertEquals("Bob", names.get(1));
    }


    @Test
    void promptPlayerNames_OnePlayer_ReturnsSingleName() {
        GameView view = viewWithInput("1", "Alice");

        List<String> names = view.promptPlayerNames();

        assertEquals(1, names.size());
        assertTrue(names.contains("Alice"));
    }

    @Test
    void promptPlayerNames_ThreePlayer_ReturnsThreeNames() {
        GameView view = viewWithInput("3", "Alice", "Bob", "Marie");

        List<String> names = view.promptPlayerNames();

        assertEquals(3, names.size());
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
        assertTrue(names.contains("Marie"));

    }

    @Test
    void promptPlayerNames_FourPlayer_ReturnsFourNames() {
        GameView view = viewWithInput("4", "Alice", "Bob", "Marie", "Camden");

        List<String> names = view.promptPlayerNames();

        assertTrue(names.size() ==4);
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
        assertTrue(names.contains("Marie"));
        assertTrue(names.contains("Camden"));

    }

    @Test
    void promptPlayerNames_FivePlayer_ReturnsFiveNames() {
        GameView view = viewWithInput("5", "Alice", "Bob", "Marie", "Camden", "North");

        List<String> names = view.promptPlayerNames();

        assertTrue(names.size() ==5);
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
        assertTrue(names.contains("Marie"));
        assertTrue(names.contains("Camden"));
        assertTrue(names.contains("North"));
    }

    @Test
    void promptPlayerNames_SixPlayer_ReturnsSixNames() {
        GameView view = viewWithInput("6", "Alice", "Bob", "Marie", "Camden", "North", "Ben");

        List<String> names = view.promptPlayerNames();

        assertTrue(names.size() ==6);
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
        assertTrue(names.contains("Marie"));
        assertTrue(names.contains("Camden"));
        assertTrue(names.contains("North"));
        assertTrue(names.contains("Ben"));
    }

    @Test
    void displayError_NullMessage_ThrowsIllegalArgumentException() {
        GameView view = new GameView();

        assertThrows(IllegalArgumentException.class, () -> view.displayError(null));
    }

    @Test
    void displayError_EmptyMessage_ShowsPrefixOnly() {
        GameView view = new GameView();
        view.displayError("");

        assertTrue(captured().contains("Error: "));
    }

    @Test
    void displayError_NormalMessage_ShowsPrefixAndMessage() {
        GameView view = new GameView();
        view.displayError("something went wrong");

        assertTrue(captured().contains("Error: something went wrong"));
    }

    @Test
    void displayCardDrawn_NullCard_ThrowsIllegalArgumentException() {
        GameView view = new GameView();

        assertThrows(IllegalArgumentException.class, () -> view.displayCardDrawn(null));
    }

}
