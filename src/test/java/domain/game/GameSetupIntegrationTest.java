package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GameSetupIntegrationTest {
    @Test
    void setupGame_ValidTwoPlayers_DealsSixCardsEachAndResetsGameState() {
        Game game = new Game(createDeck(3, 3, 10));

        game.setupGame(List.of("Alice", "Bob"));

        assertEquals(2, game.getPlayers().size());
        assertEquals("Alice", game.getCurrentPlayer().getName());
        assertEquals(0, game.getCurrentPlayerIndex());
        assertEquals(0, game.getDiscardPile().size());

        for (Player player : game.getPlayers()) {
            assertEquals(6, player.getHandSize());
            assertEquals(1, player.countCardsOfType(CardType.DEFUSE));
        }

        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.EXPLODING_KITTEN));
    }

    private Deck createDeck(int explodingKittens, int defuses, int normalCards) {
        List<Card> cards = new ArrayList<>();

        for (int count = 0; count < explodingKittens; count++) {
            cards.add(new Card(CardType.EXPLODING_KITTEN));
        }

        for (int count = 0; count < defuses; count++) {
            cards.add(new Card(CardType.DEFUSE));
        }

        for (int count = 0; count < normalCards; count++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }

        return new Deck(cards);
    }
}
