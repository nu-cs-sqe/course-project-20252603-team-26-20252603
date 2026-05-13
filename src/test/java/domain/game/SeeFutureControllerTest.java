package domain.game;

import org.junit.jupiter.api.Test;
import ui.GameView;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SeeFutureControllerTest {
    @Test
    void peekTopCards_EmptyDeck_ReturnsEmptyList() {
        Deck deck = new Deck(List.of());
        List<Card> viewedCards = deck.peekTopCards(2);

        assertEquals(0, viewedCards.size());
    }

    @Test
    void peekTopCards_OneCardDeck_ReturnsOneCard() {
        Card topCard = new Card(CardType.DEFUSE);
        Deck deck = new Deck(List.of(topCard));

        List<Card> viewedCards = deck.peekTopCards(2);

        assertEquals(List.of(topCard), viewedCards);
    }

    @Test
    void peekTopCards_DoesNotRemoveCardsFromDeck() {
        Card firstCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.EXPLODING_KITTEN);
        Deck deck = new Deck(List.of(secondCard, firstCard));

        deck.peekTopCards(2);

        assertEquals(2, deck.size());
        assertEquals(List.of(secondCard, firstCard), deck.snapshot());
    }

    @Test
    void peekTopCards_ThreeCardDeck_ReturnsOnlyTopTwoCards() {
        Card thirdCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.BEARD_CAT);
        Card firstCard = new Card(CardType.EXPLODING_KITTEN);

        Deck deck = new Deck(List.of(thirdCard, secondCard, firstCard));
        List<Card> viewedCards = deck.peekTopCards(2);

        assertEquals(List.of(firstCard, secondCard), viewedCards);

    }

    @Test
    void peekTopCards_NegativeCount_ThrowsException() {
        Deck deck = new Deck(List.of(new Card(CardType.DEFUSE)));

        assertThrows(IllegalArgumentException.class, () -> deck.peekTopCards(-1));
    }
    @Test
    void peekTopCards_CountZero_ReturnsEmptyList() {
        Deck deck = new Deck(List.of(new Card(CardType.DEFUSE)));

        List<Card> viewedCards = deck.peekTopCards(0);

        assertEquals(0, viewedCards.size());
    }
    @Test
    void constructorStoresSeeTheFutureCardType() {
        Card card = new Card(CardType.SEE_THE_FUTURE);

        assertEquals(CardType.SEE_THE_FUTURE, card.getType());
    }

    @Test
    void playSeeFuture_ReturnsTopTwoCards() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SEE_THE_FUTURE));

        Card thirdCard = new Card(CardType.DEFUSE);
        Card secondCard = new Card(CardType.BEARD_CAT);
        Card firstCard = new Card(CardType.EXPLODING_KITTEN);

        Deck drawPile = new Deck(List.of(thirdCard, secondCard, firstCard));
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        List<Card> viewedCards = controller.play(player, 0);

        assertEquals(List.of(firstCard, secondCard), viewedCards);
    }

    @Test
    void playSeeFuture_RemovesCardFromHand() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SEE_THE_FUTURE));

        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        controller.play(player, 0);

        assertEquals(0, player.getHandSize());

    }
    @Test
    void playSeeFuture_AddsCardToDiscardPile() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SEE_THE_FUTURE));

        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        controller.play(player, 0);

        assertEquals(1, discardPile.size());
        assertEquals(CardType.SEE_THE_FUTURE, discardPile.snapshot().get(0).getType());
    }

    @Test
    void playSeeFuture_SelectedCardIsDefuse_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));

        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        assertThrows(IllegalArgumentException.class,
                () -> controller.play(player, 0));
    }

    @Test
    void playSeeFuture_NegativeIndex_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SEE_THE_FUTURE));

        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        assertThrows(IndexOutOfBoundsException.class,
                () -> controller.play(player, -1));
    }

    @Test
    void playSeeFuture_IndexEqualsHandSize_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SEE_THE_FUTURE));

        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        assertThrows(IndexOutOfBoundsException.class,
                () -> controller.play(player, 1));
    }

    @Test
    void playSeeFuture_NullPlayer_ThrowsException() {
        Deck drawPile = new Deck(List.of());
        DiscardPile discardPile = new DiscardPile();
        SeeFutureController controller = new SeeFutureController(drawPile, discardPile);

        assertThrows(NullPointerException.class,
                () -> controller.play(null, 0));
    }

    @Test
    void displaySeeTheFutureCards_EmptyList_PrintsNoCardsMessage() {
        GameView view = new GameView();

        view.displaySeeTheFutureCards(List.of());
    }
}
