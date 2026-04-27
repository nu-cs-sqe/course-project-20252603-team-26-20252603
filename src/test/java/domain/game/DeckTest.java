package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Random;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class DeckTest {
    @Test
    void constructorRejectsNullCards() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Deck(null));

        assertEquals("cards must not be null", exception.getMessage());
    }

    @Test
    void shuffleDoesNothingForEmptyDeck() {
        Random random = EasyMock.createMock(Random.class);
        EasyMock.replay(random);
        Deck deck = new Deck(List.of(), random);

        deck.shuffle();

        assertEquals(0, deck.size());
        EasyMock.verify(random);
    }

    @Test
    void shuffleKeepsSingleCardDeckInPlace() {
        Random random = EasyMock.createMock(Random.class);
        Card onlyCard = EasyMock.createMock(Card.class);
        EasyMock.replay(random, onlyCard);
        Deck deck = new Deck(List.of(onlyCard), random);

        deck.shuffle();

        assertEquals(List.of(onlyCard), deck.snapshot());
        EasyMock.verify(random, onlyCard);
    }

    @Test
    void shuffleUsesInjectedRandomForMultiCardDeck() {
        Random random = EasyMock.createMock(Random.class);
        Card explodingKitten = EasyMock.createMock(Card.class);
        Card defuse = EasyMock.createMock(Card.class);
        Card other = EasyMock.createMock(Card.class);
        EasyMock.expect(random.nextInt(3)).andReturn(0);
        EasyMock.expect(random.nextInt(2)).andReturn(0);
        EasyMock.replay(random, explodingKitten, defuse, other);
        Deck deck = new Deck(List.of(explodingKitten, defuse, other), random);

        deck.shuffle();

        assertEquals(List.of(defuse, other, explodingKitten), deck.snapshot());
        EasyMock.verify(random, explodingKitten, defuse, other);
    }

    @Test
    void drawRejectsEmptyDeck() {
        Deck deck = new Deck(List.of());

        IllegalStateException exception =
                assertThrows(IllegalStateException.class, deck::draw);

        assertEquals("cannot draw from an empty deck", exception.getMessage());
    }

    @Test
    void drawRemovesTopCardFromSingleCardDeck() {
        Card onlyCard = EasyMock.createMock(Card.class);
        EasyMock.replay(onlyCard);
        Deck deck = new Deck(List.of(onlyCard));

        Card drawnCard = deck.draw();

        assertEquals(onlyCard, drawnCard);
        assertEquals(0, deck.size());
        EasyMock.verify(onlyCard);
    }

    @Test
    void drawRemovesLastCardFromMultiCardDeck() {
        Card firstCard = EasyMock.createMock(Card.class);
        Card lastCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstCard, lastCard);
        Deck deck = new Deck(List.of(firstCard, lastCard));

        Card drawnCard = deck.draw();

        assertEquals(lastCard, drawnCard);
        assertEquals(List.of(firstCard), deck.snapshot());
        EasyMock.verify(firstCard, lastCard);
    }

    @Test
    void addCardAddsToEmptyDeck() {
        Card card = EasyMock.createMock(Card.class);
        Deck deck = new Deck(List.of());
        EasyMock.replay(card);

        deck.addCard(card);

        assertEquals(List.of(card), deck.snapshot());
        EasyMock.verify(card);
    }

    @Test
    void addCardRejectsNullCard() {
        Deck deck = new Deck(List.of());

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> deck.addCard(null));

        assertEquals("card must not be null", exception.getMessage());
    }

    @Test
    void removeCardsByTypeReturnsEmptyListWhenTypeIsAbsent() {
        Card firstCard = createCardWithType(CardType.OTHER);
        Card secondCard = createCardWithType(CardType.DEFUSE);
        Deck deck = new Deck(List.of(firstCard, secondCard));

        List<Card> removedCards = deck.removeCardsByType(CardType.EXPLODING_KITTEN);

        assertEquals(List.of(), removedCards);
        assertEquals(List.of(firstCard, secondCard), deck.snapshot());
        EasyMock.verify(firstCard, secondCard);
    }

    @Test
    void removeCardsByTypeRemovesMatchingCardsOnly() {
        Card firstCard = createCardWithType(CardType.DEFUSE);
        Card secondCard = createCardWithType(CardType.OTHER);
        Card thirdCard = createCardWithType(CardType.DEFUSE);
        Deck deck = new Deck(List.of(firstCard, secondCard, thirdCard));

        List<Card> removedCards = deck.removeCardsByType(CardType.DEFUSE);

        assertEquals(List.of(firstCard, thirdCard), removedCards);
        assertEquals(List.of(secondCard), deck.snapshot());
        EasyMock.verify(firstCard, secondCard, thirdCard);
    }

    @Test
    void removeCardsByTypeRejectsNullType() {
        Card card = createCardWithType(CardType.OTHER);
        Deck deck = new Deck(List.of(card));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> deck.removeCardsByType(null));

        assertEquals("card type must not be null", exception.getMessage());
        EasyMock.verify(card);
    }

    private Card createCardWithType(CardType type) {
        Card card = EasyMock.createMock(Card.class);
        EasyMock.expect(card.getType()).andStubReturn(type);
        EasyMock.replay(card);
        return card;
    }
}
