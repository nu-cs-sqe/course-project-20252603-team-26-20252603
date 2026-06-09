package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



class CatCardTest {
    @Test
    void canSubmitCard_OneBeardCat_ReturnsFalse() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));

        assertFalse(player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_TwoMatchingBeardCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));

        assertTrue(player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_ThreeMatchingBeardCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));

        assertTrue(player.canSubmitCard(1));
    }

    @Test
    void canSubmitCard_TwoDifferentCatCards_ReturnsFalse() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.TACOCAT));

        assertFalse(player.canSubmitCard(0));
    }

    @Test
    void canSubmitCard_MatchingHairyPotatoCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.HAIRY_POTATO_CAT));
        player.addCard(new Card(CardType.HAIRY_POTATO_CAT));

        assertTrue(player.canSubmitCard(1));
    }

    @Test
    void canSubmitCard_MatchingTacocats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.TACOCAT));
        player.addCard(new Card(CardType.TACOCAT));

        assertTrue(player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_MatchingRainbowRalphingCats_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.RAINBOW_RALPHING_CAT));
        player.addCard(new Card(CardType.RAINBOW_RALPHING_CAT));

        assertTrue(player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_PlaceholderCard_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.PLACEHOLDER_CARD));

        assertTrue(player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_DefuseCard_ReturnsFalse() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));

        assertFalse(player.canSubmitCard(0));
    }
    @Test
    void canSubmitCard_NegativeIndex_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> player.canSubmitCard(-1));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
    }
    @Test
    void canSubmitCard_IndexEqualsHandSize_ThrowsException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));
        player.addCard(new Card(CardType.BEARD_CAT));

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> player.canSubmitCard(2));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
    }
    @Test
    void canSubmitCard_HandEmpty_ThrowsException() {
        Player player = new Player("Sophie");

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> player.canSubmitCard(0));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
    }
    @Test
    void chooseCard_BeardCat_ReturnsCatCardExplanation() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.BEARD_CAT));

        assertEquals(
                "Cat Card: This card is powerless on its own. Play two matching Cat Cards as a pair to steal a random card from another player.",
                player.chooseCard(0)
        );
    }

    @Test
    void canSubmitCard_SwapTopAndBottom_ReturnsTrue() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SWAP_TOP_AND_BOTTOM));

        assertTrue(player.canSubmitCard(0));
    }
}
