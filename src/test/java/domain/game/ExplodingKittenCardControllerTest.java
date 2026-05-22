package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ExplodingKittenCardControllerTest {
    @Test
    void constructor_NullGame_ThrowsException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new ExplodingKittenCardController(null));

        assertEquals("game must not be null", exception.getMessage());
    }
}
