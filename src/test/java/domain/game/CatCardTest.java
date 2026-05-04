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
    @Test
    void canSubmitCard_ThreeMatchingBeardCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));

        assertEquals(true, player.canSubmitCard(1));
    }

    @Test
    void canSubmitCard_TwoDifferentCatCards_ReturnsFalse() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.TACOCAT));

        assertEquals(false, player.canSubmitCard(0));
    }

    @Test
    void canSubmitCard_MatchingHairyPotatoCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.HAIRY_POTATO_CAT));
        player.addCard(new Card(CardType.HAIRY_POTATO_CAT));

        assertEquals(true, player.canSubmitCard(1));
    }

    @Test
    void canSubmitCard_MatchingTacocats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.TACOCAT));
        player.addCard(new Card(CardType.TACOCAT));

        assertEquals(true, player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_MatchingRainbowRalphingCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.RAINBOW_RALPHING_CAT));
        player.addCard(new Card(CardType.RAINBOW_RALPHING_CAT));

        assertEquals(true, player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_PlaceholderCard_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.PLACEHOLDER_CARD));

        assertEquals(true, player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_DefuseCard_ReturnsFalse() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));

        assertEquals(false, player.canSubmitCard(0));
    }

}
