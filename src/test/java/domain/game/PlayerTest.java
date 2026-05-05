package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

class PlayerTest {
    @Test
    void constructorRejectsNullName() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Player(null));

        assertEquals("name must not be blank", exception.getMessage());
    }

    @Test
    void constructorRejectsEmptyName() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Player(""));

        assertEquals("name must not be blank", exception.getMessage());
    }

    @Test
    void getNameReturnsProvidedName() {
        Player player = new Player("Avery");

        assertEquals("Avery", player.getName());
    }

    @Test
    void getHandSizeStartsAtZero() {
        Player player = new Player("Avery");

        assertEquals(0, player.getHandSize());
    }

    @Test
    void addCardToEmptyHandIncreasesHandSize() {
        Player player = new Player("Avery");
        Card card = EasyMock.createMock(Card.class);
        EasyMock.replay(card);

        player.addCard(card);

        assertEquals(1, player.getHandSize());
        assertEquals(card, player.getHandSnapshot().get(0));
        EasyMock.verify(card);
    }

    @Test
    void addCardToNonEmptyHandAppendsCard() {
        Player player = new Player("Avery");
        Card firstCard = EasyMock.createMock(Card.class);
        Card secondCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstCard, secondCard);

        player.addCard(firstCard);

        player.addCard(secondCard);

        assertEquals(2, player.getHandSize());
        assertEquals(firstCard, player.getHandSnapshot().get(0));
        assertEquals(secondCard, player.getHandSnapshot().get(1));
        EasyMock.verify(firstCard, secondCard);
    }

    @Test
    void addCardRejectsNullCard() {
        Player player = new Player("Avery");

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> player.addCard(null));

        assertEquals("card must not be null", exception.getMessage());
    }

    @Test
    void removeCard_FirstIndex_RemovesCard() {
        Player player = new Player("Avery");
        Card firstCard = EasyMock.createMock(Card.class);
        Card secondCard = EasyMock.createMock(Card.class);
        Card thirdCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstCard, secondCard, thirdCard);

        player.addCard(firstCard);
        player.addCard(secondCard);
        player.addCard(thirdCard);

        assertEquals(3, player.getHandSize());

        player.removeCard(0);

        assertEquals(2, player.getHandSize());
        assertEquals(secondCard, player.getHandSnapshot().get(0));
        assertEquals(thirdCard, player.getHandSnapshot().get(1));
    }

    @Test
    void removeCard_LastIndex_RemovesCard() {
        Player player = new Player("Avery");
        Card firstCard = EasyMock.createMock(Card.class);
        Card secondCard = EasyMock.createMock(Card.class);
        Card thirdCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstCard, secondCard, thirdCard);

        player.addCard(firstCard);
        player.addCard(secondCard);
        player.addCard(thirdCard);

        assertEquals(3, player.getHandSize());

        player.removeCard(2);

        assertEquals(2, player.getHandSize());
        assertEquals(firstCard, player.getHandSnapshot().get(0));
        assertEquals(secondCard, player.getHandSnapshot().get(1));
    }

    @Test
    void removeCard_MiddleIndex_RemovesCard() {
        Player player = new Player("Avery");
        Card firstCard = EasyMock.createMock(Card.class);
        Card secondCard = EasyMock.createMock(Card.class);
        Card thirdCard = EasyMock.createMock(Card.class);
        EasyMock.replay(firstCard, secondCard, thirdCard);

        player.addCard(firstCard);
        player.addCard(secondCard);
        player.addCard(thirdCard);

        assertEquals(3, player.getHandSize());

        player.removeCard(1);

        assertEquals(2, player.getHandSize());
        assertEquals(firstCard, player.getHandSnapshot().get(0));
        assertEquals(thirdCard, player.getHandSnapshot().get(1));
    }
}
