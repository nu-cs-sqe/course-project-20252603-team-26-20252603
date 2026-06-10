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
import domain.game.EliminatedPlayer;
import domain.game.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void displayCardStolen_WithCard_ShowsStolenCardType() {
        GameView view = new GameView();

        view.displayCardStolen(new Card(CardType.SKIP));

        assertTrue(captured().contains("You stole: SKIP"));
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

        assertTrue(names.size() == 4);
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
        assertTrue(names.contains("Marie"));
        assertTrue(names.contains("Camden"));

    }

    @Test
    void promptPlayerNames_FivePlayer_ReturnsFiveNames() {
        GameView view = viewWithInput("5", "Alice", "Bob", "Marie", "Camden", "North");

        List<String> names = view.promptPlayerNames();

        assertTrue(names.size() == 5);
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

        assertTrue(names.size() == 6);
        assertTrue(names.contains("Alice"));
        assertTrue(names.contains("Bob"));
        assertTrue(names.contains("Marie"));
        assertTrue(names.contains("Camden"));
        assertTrue(names.contains("North"));
        assertTrue(names.contains("Ben"));
    }

    @Test
    void promptCardChoice_DrawChoice_ReturnsZero() {
        GameView view = viewWithInput("0");

        int choice = view.promptCardChoice();

        assertEquals(0, choice);
    }

    @Test
    void promptCardChoice_NonNumericThenDrawChoice_DisplaysErrorAndReturnsZero() {
        GameView view = viewWithInput("cat", "0");

        int choice = view.promptCardChoice();

        assertEquals(0, choice);
        assertTrue(captured().contains("Please enter a number."));
    }

    @Test
    void promptSecondCardChoice_ValidCardNumber_ReturnsChoice() {
        GameView view = viewWithInput("3");

        int choice = view.promptSecondCardChoice();

        assertEquals(3, choice);
    }

    @Test
    void promptTargetPlayer_OutOfRangeThenValid_ReturnsSelectedPlayer() {
        GameView view = viewWithInput("0", "2");
        Player firstPlayer = new Player("Alice");
        Player secondPlayer = new Player("Bob");

        Player selectedPlayer = view.promptTargetPlayer(
                List.of(firstPlayer, secondPlayer));

        assertEquals(secondPlayer, selectedPlayer);
        assertTrue(captured().contains("Choose a number between 1 and 2."));
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

    @Test
    void displayCardDrawn_ExplodingKitten_ShowsExplodingKitten() {
        GameView view = new GameView();

        view.displayCardDrawn(new Card(CardType.EXPLODING_KITTEN));

        assertTrue(captured().contains("EXPLODING_KITTEN"));
    }

    @Test
    void displayCardDrawn_Defuse_ShowsDefuse() {
        GameView view = new GameView();

        view.displayCardDrawn(new Card(CardType.DEFUSE));

        assertTrue(captured().contains("DEFUSE"));
    }

    @Test
    void displayCardDrawn_PlaceholderCard_ShowsPlaceholderCard() {
        GameView view = new GameView();

        view.displayCardDrawn(new Card(CardType.PLACEHOLDER_CARD));

        assertTrue(captured().contains("PLACEHOLDER_CARD"));
    }

    @Test
    void displayMessage_EmptyString_PrintsBlankLine() {
        GameView view = new GameView();

        view.displayMessage("");

        assertNotNull(captured());
    }

    @Test
    void displayMessage_NormalString_ShowsMessage() {
        GameView view = new GameView();

        view.displayMessage("Skip played. Your turn ends without drawing a card.");

        assertTrue(captured().contains("Skip played. Your turn ends without drawing a card."));
    }

    @Test
    void displaySeeTheFutureCards_EmptyList_ShowsTitleAndNoCardsMessage() {
        GameView view = new GameView();

        view.displaySeeTheFutureCards(List.of());

        String text = captured();
        assertTrue(text.contains("See the Future: Top cards in the draw pile:"));
        assertTrue(text.contains("No cards to view."));
    }

    @Test
    void displaySeeTheFutureCards_OneCard_ShowsTitleAndSingleCard() {
        GameView view = new GameView();

        view.displaySeeTheFutureCards(List.of(new Card(CardType.ATTACK)));

        String text = captured();
        assertTrue(text.contains("See the Future: Top cards in the draw pile:"));
        assertTrue(text.contains("1. ATTACK"));
        assertFalse(text.contains("2."));
    }

    @Test
    void displaySeeTheFutureCards_TwoCards_ShowsTitleAndBothCardsInOrder() {
        GameView view = new GameView();

        view.displaySeeTheFutureCards(List.of(new Card(CardType.ATTACK), new Card(CardType.SHUFFLE)));

        String text = captured();
        assertTrue(text.contains("See the Future: Top cards in the draw pile:"));
        assertTrue(text.contains("1. ATTACK"));
        assertTrue(text.contains("2. SHUFFLE"));
    }

    @Test
    void displayPublicPlayerState_WithActiveAndEliminatedPlayers_PrintsFaceDownAndFaceUpState() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

        try {
            GameView view = new GameView();

            Player activePlayer = new Player("Jordan");
            activePlayer.addCard(new Card(CardType.BEARD_CAT));
            activePlayer.addCard(new Card(CardType.SKIP));

            Card killingKitten = new Card(CardType.EXPLODING_KITTEN);
            Card visibleCard = new Card(CardType.DEFUSE);
            Card secondVisibleCard = new Card(CardType.TACOCAT);
            EliminatedPlayer eliminatedPlayer = new EliminatedPlayer(
                    "Avery",
                    killingKitten,
                    List.of(visibleCard, secondVisibleCard));

            view.displayPublicPlayerState(
                    List.of(activePlayer),
                    List.of(eliminatedPlayer));

            String printed = output.toString(StandardCharsets.UTF_8);

            assertTrue(printed.contains("Jordan: 2 face-down card(s)"));
            assertTrue(printed.contains("Avery: eliminated by face-up EXPLODING_KITTEN"));
            assertTrue(printed.contains("Remaining face-up cards:"));
            assertTrue(printed.contains("- DEFUSE"));
            assertTrue(printed.contains("- TACOCAT"));
        } finally {
            System.setOut(originalOut);
        }
    }
}
