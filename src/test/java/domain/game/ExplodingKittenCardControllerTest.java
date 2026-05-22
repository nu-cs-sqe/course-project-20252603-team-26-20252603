package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExplodingKittenCardControllerTest {
    @Test
    void constructor_NullGame_ThrowsException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new ExplodingKittenCardController(null));

        assertEquals("game must not be null", exception.getMessage());
    }

    @Test
    void play_NoDefuseEmptyHandWithThreePlayers_EliminatesPlayerAndGameContinues() {
        Game game = createStartedGame(3);
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        ExplodingKittenCardController controller = new ExplodingKittenCardController(game);

        boolean defused = controller.play(currentPlayer, new Card(CardType.EXPLODING_KITTEN));

        assertFalse(defused);
        assertFalse(game.getPlayers().contains(currentPlayer));
        assertEquals(2, game.getPlayers().size());
        assertFalse(game.isWon());
    }

    private Game createStartedGame(int playerCount) {
        Game game = new Game(createDeckForPlayers(playerCount));
        game.setupGame(createPlayerNames(playerCount));
        return game;
    }

    private Deck createDeckForPlayers(int playerCount) {
        List<Card> cards = new ArrayList<>();
        for (int count = 0; count < playerCount - 1; count++) {
            cards.add(new Card(CardType.EXPLODING_KITTEN));
        }
        for (int count = 0; count < playerCount; count++) {
            cards.add(new Card(CardType.DEFUSE));
        }
        for (int count = 0; count < playerCount * 5; count++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        return new Deck(cards);
    }

    private List<String> createPlayerNames(int playerCount) {
        List<String> names = new ArrayList<>();
        for (int count = 0; count < playerCount; count++) {
            names.add("Player " + count);
        }
        return names;
    }

    private void clearHand(Player player) {
        while (player.getHandSize() > 0) {
            player.removeCard(0);
        }
    }
}
