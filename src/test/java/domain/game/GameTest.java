package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    void constructorRejectsNullDrawPile() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Game(null));

        assertEquals("draw pile must not be null", exception.getMessage());
    }

    @Test
    void getPlayersStartsEmptyBeforeSetup() {
        Game game = new Game(createDeck(1, 2, 10));

        assertEquals(List.of(), game.getPlayers());
    }

    @Test
    void getCurrentPlayerRejectsBeforeSetup() {
        Game game = new Game(createDeck(1, 2, 10));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, game::getCurrentPlayer);

        assertEquals("game has not been set up", exception.getMessage());
    }

    @Test
    void setupGameRejectsNullPlayerNames() {
        Game game = new Game(createDeck(2, 2, 10));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> game.setupGame(null));

        assertEquals("player names must not be null", exception.getMessage());
    }

    @Test
    void setupGameRejectsTooFewPlayers() {
        Game game = new Game(createDeck(2, 2, 10));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> game.setupGame(List.of("Avery")));

        assertEquals("player count must be between 2 and 4", exception.getMessage());
    }

    @Test
    void setupGameRejectsTooManyPlayers() {
        Game game = new Game(createDeck(4, 5, 30));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan", "Casey", "Riley", "Morgan")));

        assertEquals("player count must be between 2 and 4", exception.getMessage());
    }

    @Test
    void setupGameRejectsDeckWithoutEnoughDefuses() {
        Game game = new Game(createDeck(2, 1, 10));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan")));

        assertEquals("deck must contain at least one defuse per player", exception.getMessage());
    }

    @Test
    void setupGameRejectsDeckWithoutEnoughExplodingKittens() {
        Game game = new Game(createDeck(2, 4, 20));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan", "Casey", "Riley")));

        assertEquals("deck must contain enough exploding kittens for setup", exception.getMessage());
    }

    @Test
    void setupGameRejectsDeckWithoutEnoughOpeningCards() {
        Game game = new Game(createDeck(2, 2, 9));

        IllegalStateException exception =
                assertThrows(IllegalStateException.class,
                        () -> game.setupGame(List.of("Avery", "Jordan")));

        assertEquals(
                "deck must contain enough non-special cards to deal opening hands",
                exception.getMessage());
    }

    @Test
    void setupGameConfiguresMinimumPlayerGame() {
        Game game = new Game(createDeck(3, 3, 12));

        game.setupGame(List.of("Avery", "Jordan"));

        assertEquals(2, game.getPlayers().size());
        assertEquals("Avery", game.getPlayers().get(0).getName());
        assertEquals("Jordan", game.getPlayers().get(1).getName());
        assertEquals("Avery", game.getCurrentPlayer().getName());
        assertEquals(0, game.getDiscardPile().size());
        assertPlayersHaveOpeningHands(game.getPlayers());
        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.EXPLODING_KITTEN));
        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.DEFUSE));
        assertEquals(4, game.getDrawPile().size());
    }

    @Test
    void setupGameConfiguresMaximumPlayerGame() {
        Game game = new Game(createDeck(4, 5, 20));

        game.setupGame(List.of("Avery", "Jordan", "Casey", "Riley"));

        assertEquals(4, game.getPlayers().size());
        assertEquals("Avery", game.getCurrentPlayer().getName());
        assertPlayersHaveOpeningHands(game.getPlayers());
        assertEquals(3, game.getDrawPile().countCardsOfType(CardType.EXPLODING_KITTEN));
        assertEquals(1, game.getDrawPile().countCardsOfType(CardType.DEFUSE));
        assertEquals(4, game.getDrawPile().size());
    }

    private void assertPlayersHaveOpeningHands(List<Player> players) {
        for (Player player : players) {
            assertEquals(6, player.getHandSize());
            assertEquals(1, player.countCardsOfType(CardType.DEFUSE));
        }
    }

    private Deck createDeck(int explodingKittens, int defuses, int others) {
        List<Card> cards = new ArrayList<>();
        for (int count = 0; count < explodingKittens; count++) {
            cards.add(createCardWithType(CardType.EXPLODING_KITTEN));
        }
        for (int count = 0; count < defuses; count++) {
            cards.add(createCardWithType(CardType.DEFUSE));
        }
        for (int count = 0; count < others; count++) {
            cards.add(createCardWithType(CardType.OTHER));
        }
        return new Deck(cards);
    }

    private Card createCardWithType(CardType type) {
        Card card = EasyMock.createMock(Card.class);
        EasyMock.expect(card.getType()).andStubReturn(type);
        EasyMock.replay(card);
        return card;
    }
}
