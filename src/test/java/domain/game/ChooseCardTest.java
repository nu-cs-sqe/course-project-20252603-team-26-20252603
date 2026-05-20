package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChooseCardTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Sophie");
        player.addCard(new Card(CardType.DEFUSE));
        player.addCard(new Card(CardType.PLACEHOLDER_CARD));
        player.addCard(new Card(CardType.PLACEHOLDER_CARD));
    }

    @Test
    void chooseCard_FirstIndex_ReturnsDefuseExplanation() {
        String explanation = player.chooseCard(0);

        assertEquals(
                "Defuse: Use this card to avoid exploding after drawing an Exploding Kitten.",
                explanation
        );
        assertEquals(3, player.getHandSize());
    }

    @Test
    void chooseCard_LastIndex_ReturnsPlaceholderCardExplanation() {
        String explanation = player.chooseCard(2);

        assertEquals(
                "Placeholder Card: This card is choosable, but its effect will be implemented later.",
                explanation
        );
        assertEquals(3, player.getHandSize());
    }
    @Test
    void chooseCard_MiddleIndex_ReturnsPlaceholderCardExplanation() {
        String explanation = player.chooseCard(1);

        assertEquals(
                "Placeholder Card: This card is choosable, but its effect will be implemented later.",
                explanation
        );
        assertEquals(3, player.getHandSize());
    }
    @Test
    void chooseCard_NegativeIndex_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.chooseCard(-1));
        assertEquals(3, player.getHandSize());
    }
    @Test
    void chooseCard_IndexEqualsHandSize_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.chooseCard(3));
        assertEquals(3, player.getHandSize());
    }
    @Test
    void chooseCard_HandHasOneCard_ReturnsExplanation() {
        Player oneCardHand = new Player("Sophie");
        oneCardHand.addCard(new Card(CardType.DEFUSE));

        String explanation = oneCardHand.chooseCard(0);

        assertEquals(
                "Defuse: Use this card to avoid exploding after drawing an Exploding Kitten.",
                explanation
        );
        assertEquals(1, oneCardHand.getHandSize());
    }
    @Test
    void chooseCard_HandEmpty_ThrowsException() {
        Player emptyHand = new Player("Sophie");

        assertThrows(IllegalArgumentException.class, () -> emptyHand.chooseCard(0));
        assertEquals(0, emptyHand.getHandSize());
    }

}
