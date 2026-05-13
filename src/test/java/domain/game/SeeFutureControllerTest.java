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
}
