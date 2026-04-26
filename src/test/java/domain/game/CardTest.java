package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.api.Test;

class CardTest {
    @ParameterizedTest
    @EnumSource(CardType.class)
    void constructorStoresProvidedCardType(CardType type) {
        Card card = new Card(type);
        assertEquals(type, card.getType());
    }

    @Test
    void constructorRejectsNullType() {
        NullPointerException exception =
        assertThrows(NullPointerException.class, () -> new Card(null));

        assertEquals("type must not be null", exception.getMessage());
    }
}
