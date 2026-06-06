package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}