package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeckFactoryTest {
    @Test
    void standardDeck_TotalCount_Returns38() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(38, deck.size());
    }

    @Test
    void standardDeck_ContainsThreeExplodingKittens() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(3, deck.countCardsOfType(CardType.EXPLODING_KITTEN));
    }

    @Test
    void standardDeck_ContainsFiveDefuses() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(5, deck.countCardsOfType(CardType.DEFUSE));
    }

    @Test
    void standardDeck_ContainsThreeAttacks() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(3, deck.countCardsOfType(CardType.ATTACK));
    }

    @Test
    void standardDeck_ContainsThreeSkips() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(3, deck.countCardsOfType(CardType.SKIP));
    }
}
