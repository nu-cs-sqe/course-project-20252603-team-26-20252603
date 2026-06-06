package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class CardPlayIntegrationTest {
    @Test
    void seeFuture_PlayValidCard_RemovesCardDiscardsItAndPeeksTopTwoWithoutChangingDeckSize() {
        Player player = new Player("Sophie");
        Card seeFuture = new Card(CardType.SEE_THE_FUTURE);
        player.addCard(seeFuture);

        Card bottomCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.BEARD_CAT);
        Card topCard = new Card(CardType.EXPLODING_KITTEN);
        Deck drawPile = new Deck(List.of(bottomCard, secondCard, topCard));
        List<Card> drawPileBeforePlay = drawPile.snapshot();

        DiscardPile discardPile = new DiscardPile();
        SeeFutureCardController controller =
                new SeeFutureCardController(drawPile, discardPile);

        List<Card> viewedCards = controller.play(player, 0);

        assertEquals(List.of(topCard, secondCard), viewedCards);
        assertEquals(0, player.getHandSize());
        assertEquals(List.of(seeFuture), discardPile.snapshot());
        assertEquals(drawPileBeforePlay, drawPile.snapshot());
        assertEquals(3, drawPile.size());
    }

    @Test
    void skip_PlayValidCard_RemovesCardDiscardsItAndReturnsTrue() {
        Player player = new Player("Sophie");
        Card skip = new Card(CardType.SKIP);
        player.addCard(skip);

        Deck drawPile = new Deck(List.of(
                new Card(CardType.BEARD_CAT),
                new Card(CardType.DEFUSE)));
        List<Card> drawPileBeforePlay = drawPile.snapshot();

        DiscardPile discardPile = new DiscardPile();
        SkipCardController controller = new SkipCardController(discardPile);

        boolean skipped = controller.play(player, 0);

        assertTrue(skipped);
        assertEquals(0, player.getHandSize());
        assertEquals(List.of(skip), discardPile.snapshot());
        assertEquals(drawPileBeforePlay, drawPile.snapshot());
    }

    @Test
    void shuffle_PlayValidCard_RemovesCardDiscardsItAndPreservesDeckSize() {
        Game game = new Game(createDeckForPlayers(2, new CountingZeroRandom()));
        game.setupGame(List.of("Sophie", "Jordan"));

        Player currentPlayer = game.getCurrentPlayer();
        clearHand(currentPlayer);

        Card shuffle = new Card(CardType.SHUFFLE);
        currentPlayer.addCard(shuffle);

        Card firstDrawPileCard = new Card(CardType.BEARD_CAT);
        Card secondDrawPileCard = new Card(CardType.DEFUSE);
        clearDrawPile(game.getDrawPile());
        game.getDrawPile().addCard(firstDrawPileCard);
        game.getDrawPile().addCard(secondDrawPileCard);

        List<Card> drawPileBeforePlay = game.getDrawPile().snapshot();

        ShuffleCardController controller = new ShuffleCardController();

        controller.play(game, 0);

        assertEquals(0, currentPlayer.getHandSize());
        assertEquals(List.of(shuffle), game.getDiscardPile().snapshot());
        assertEquals(drawPileBeforePlay.size(), game.getDrawPile().size());
        assertTrue(game.getDrawPile().snapshot().containsAll(drawPileBeforePlay));
        assertTrue(drawPileBeforePlay.containsAll(game.getDrawPile().snapshot()));
    }

    private Deck createDeckForPlayers(int playerCount, Random random) {
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

        return new Deck(cards, random);
    }

    private void clearHand(Player player) {
        while (player.getHandSize() > 0) {
            player.removeCard(0);
        }
    }

    private void clearDrawPile(Deck drawPile) {
        while (drawPile.size() > 0) {
            drawPile.draw();
        }
    }

    private static final class CountingZeroRandom extends Random {
        @Override
        public int nextInt(int bound) {
            return 0;
        }
    }

}