package domain.game;

import org.junit.jupiter.api.Test;

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
}
