package ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import domain.game.Card;
import domain.game.CardType;
import domain.game.EliminatedPlayer;
import domain.game.Player;
import org.junit.jupiter.api.Test;

public class GameViewTest {
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
