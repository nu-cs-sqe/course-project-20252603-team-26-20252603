package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeckFactoryTest {
    @Test
    void standardDeck_TotalCount_Returns38() {
        Deck deck = DeckFactory.standardDeck();

        assertEquals(38, deck.size());
    }
}
