package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkipCardControllerTest {
    @Test
    void constructorStoresSkipCardType() {
        Card card = new Card(CardType.SKIP);

        assertEquals(CardType.SKIP, card.getType());
    }
}
