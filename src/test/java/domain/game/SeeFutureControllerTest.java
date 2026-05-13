package domain.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeeFutureControllerTest {
    @Test
    void peekTopCards_EmptyDeck_ReturnsEmptyList() {
        Deck deck = new Deck(List.of());
        List<Card> viewedCards = deck.peekTopCards(2);

        assertEquals(0, viewedCards.size());
    }

    @Test
    void playSeeFuture_OneCardInDrawPile_ReturnsOneCard() {
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
}
