package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CatCardTest {
    @Test
    void canSubmitCard_OneBeardCat_ReturnsFalse() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));

        assertEquals(false, player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_TwoMatchingBeardCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));

        assertEquals(true, player.canSubmitCard(0));
    }

}
