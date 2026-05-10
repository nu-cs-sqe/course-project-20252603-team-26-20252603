package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ShuffleCardControllerTest {
    @Test
    void play_ShuffleOnlyCardWithMultiCardDrawPile_ShufflesDrawPileAndDiscardsCard() {
        CountingZeroRandom random = new CountingZeroRandom();
        Game game = createStartedGame(2, 3, 10, random);
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card shuffleCard = new Card(CardType.SHUFFLE);
        currentPlayer.addCard(shuffleCard);
        List<Card> drawPileBeforeShuffle = game.getDrawPile().snapshot();
        ShuffleCardController controller = new ShuffleCardController();

        controller.play(game, 0);

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(shuffleCard), game.getDiscardPile().snapshot());
        assertEquals(
                List.of(drawPileBeforeShuffle.get(1), drawPileBeforeShuffle.get(0)),
                game.getDrawPile().snapshot());
        assertEquals(List.of(2), random.getBoundsSinceReset());
    }

    @Test
    void play_ShuffleAtLastIndexWithOneCardDrawPile_DiscardsShuffleAndLeavesDrawPile() {
        CountingZeroRandom random = new CountingZeroRandom();
        Game game = createStartedGame(1, 2, 10, random);
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card placeholderCard = new Card(CardType.PLACEHOLDER_CARD);
        Card shuffleCard = new Card(CardType.SHUFFLE);
        currentPlayer.addCard(placeholderCard);
        currentPlayer.addCard(shuffleCard);
        List<Card> drawPileBeforeShuffle = game.getDrawPile().snapshot();
        ShuffleCardController controller = new ShuffleCardController();

        controller.play(game, 1);

        assertEquals(List.of(placeholderCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(shuffleCard), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforeShuffle, game.getDrawPile().snapshot());
        assertEquals(List.of(), random.getBoundsSinceReset());
    }

    @Test
    void play_ShuffleOnlyCardWithEmptyDrawPile_DiscardsShuffleAndLeavesDrawPileEmpty() {
        CountingZeroRandom random = new CountingZeroRandom();
        Game game = createStartedGame(1, 2, 10, random);
        game.getDrawPile().draw();
        random.resetCalls();
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card shuffleCard = new Card(CardType.SHUFFLE);
        currentPlayer.addCard(shuffleCard);
        ShuffleCardController controller = new ShuffleCardController();

        controller.play(game, 0);

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(shuffleCard), game.getDiscardPile().snapshot());
        assertEquals(List.of(), game.getDrawPile().snapshot());
        assertEquals(List.of(), random.getBoundsSinceReset());
    }

    @Test
    void play_NonShuffleCard_ThrowsExceptionAndLeavesStateUnchanged() {
        CountingZeroRandom random = new CountingZeroRandom();
        Game game = createStartedGame(2, 3, 10, random);
        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);
        Card placeholderCard = new Card(CardType.PLACEHOLDER_CARD);
        currentPlayer.addCard(placeholderCard);
        List<Card> drawPileBeforePlay = game.getDrawPile().snapshot();
        ShuffleCardController controller = new ShuffleCardController();

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> controller.play(game, 0));

        assertEquals("selected card is not a shuffle card", exception.getMessage());
        assertEquals(List.of(placeholderCard), currentPlayer.getHandSnapshot());
        assertEquals(List.of(), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforePlay, game.getDrawPile().snapshot());
        assertEquals(List.of(), random.getBoundsSinceReset());
    }

    private Game createStartedGame(
            int explodingKittens, int defuses, int others, CountingZeroRandom random) {
        Game game = new Game(createDeck(explodingKittens, defuses, others, random));
        game.setupGame(List.of("Avery", "Jordan"));
        random.resetCalls();
        return game;
    }

    private Deck createDeck(int explodingKittens, int defuses, int others, Random random) {
        List<Card> cards = new ArrayList<>();
        for (int count = 0; count < explodingKittens; count++) {
            cards.add(new Card(CardType.EXPLODING_KITTEN));
        }
        for (int count = 0; count < defuses; count++) {
            cards.add(new Card(CardType.DEFUSE));
        }
        for (int count = 0; count < others; count++) {
            cards.add(new Card(CardType.PLACEHOLDER_CARD));
        }
        return new Deck(cards, random);
    }

    private void clearHand(Player player) {
        while (player.getHandSize() > 0) {
            player.removeCard(0);
        }
    }

    private static final class CountingZeroRandom extends Random {
        private final List<Integer> boundsSinceReset = new ArrayList<>();

        @Override
        public int nextInt(int bound) {
            boundsSinceReset.add(bound);
            return 0;
        }

        void resetCalls() {
            boundsSinceReset.clear();
        }

        List<Integer> getBoundsSinceReset() {
            return List.copyOf(boundsSinceReset);
        }
    }
}
