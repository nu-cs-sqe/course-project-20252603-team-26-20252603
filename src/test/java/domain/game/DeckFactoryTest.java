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

    @Test
    void standardDeck_ContainsFourSeeTheFutureCards() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(4, deck.countCardsOfType(CardType.SEE_THE_FUTURE));
    }

    @Test
    void standardDeck_ContainsFourShuffles() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(4, deck.countCardsOfType(CardType.SHUFFLE));
    }

    @Test
    void standardDeck_ContainsFourOfEachCatCard() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(4, deck.countCardsOfType(CardType.BEARD_CAT));
        assertEquals(4, deck.countCardsOfType(CardType.HAIRY_POTATO_CAT));
        assertEquals(4, deck.countCardsOfType(CardType.TACOCAT));
        assertEquals(4, deck.countCardsOfType(CardType.RAINBOW_RALPHING_CAT));
    }

    @Test
    void completeDeck_ContainsTwoOfEachInventedCard() {
        Deck deck = DeckFactory.completeDeck();

        assertEquals(50, deck.size());
        assertEquals(2, deck.countCardsOfType(CardType.SUPER_SKIP));
        assertEquals(2, deck.countCardsOfType(CardType.REVERSE));
        assertEquals(2, deck.countCardsOfType(CardType.BURY));
        assertEquals(2, deck.countCardsOfType(CardType.SWAP_TOP_AND_BOTTOM));
        assertEquals(2, deck.countCardsOfType(CardType.DRAW_FROM_BOTTOM));
        assertEquals(2, deck.countCardsOfType(CardType.TARGETED_ATTACK));
    }
}
