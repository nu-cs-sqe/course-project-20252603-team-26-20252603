package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class DiscardPileTest {
    @Test
    void constructorStartsWithEmptyPile() {
        DiscardPile discardPile = new DiscardPile();

        assertEquals(0, discardPile.size());
    }

    @Test
    void addPlacesCardOnEmptyPile() {
        Card card = EasyMock.createMock(Card.class);
        DiscardPile discardPile = new DiscardPile();
        EasyMock.replay(card);

        discardPile.add(card);

        assertEquals(1, discardPile.size());
        assertEquals(card, discardPile.snapshot().get(0));
        EasyMock.verify(card);
    }

    @Test
    void addAppendsCardToExistingPile() {
        Card firstCard = EasyMock.createMock(Card.class);
        Card secondCard = EasyMock.createMock(Card.class);
        DiscardPile discardPile = new DiscardPile();
        EasyMock.replay(firstCard, secondCard);
        discardPile.add(firstCard);

        discardPile.add(secondCard);

        assertEquals(2, discardPile.size());
        assertEquals(firstCard, discardPile.snapshot().get(0));
        assertEquals(secondCard, discardPile.snapshot().get(1));
        EasyMock.verify(firstCard, secondCard);
    }

    @Test
    void addRejectsNullCard() {
        DiscardPile discardPile = new DiscardPile();

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> discardPile.add(null));

        assertEquals("card must not be null", exception.getMessage());
    }
}
