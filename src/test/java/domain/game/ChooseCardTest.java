package domain.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class ChooseCardTest {
    private Hand hand;

    @BeforeEach
    void setUp() {
        hand = new Hand();
        hand.addCard(new Card(CardType.EXPLODING_KITTEN));
        hand.addCard(new Card(CardType.DEFUSE));
        hand.addCard(new Card(CardType.PLACEHOLDER_CARD));
    }

    @Test
    void chooseCard_FirstIndex_ReturnsDefuseExplanation() {
        String explanation = hand.chooseCard(0);

        assertEquals(
                "Defuse: Use this card to avoid exploding after drawing an Exploding Kitten.",
                explanation
        );
        assertEquals(3, hand.getHandSize());
    }

    @Test
    void chooseCard_LastIndex_ReturnsPlaceholderCardExplanation() {
        String explanation = hand.chooseCard(2);

        assertEquals(
                "Placeholder Card: This card is choosable, but its effect will be implemented later.",
                explanation
        );
        assertEquals(3, hand.getHandSize());
    }
    @Test
    void chooseCard_MiddleIndex_ReturnsPlaceholderCardExplanation() {
        String explanation = hand.chooseCard(1);

        assertEquals(
                "Placeholder Card: This card is choosable, but its effect will be implemented later.",
                explanation
        );
        assertEquals(3, hand.getHandSize());
    }
    @Test
    void chooseCard_NegativeIndex_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> hand.chooseCard(-1));
        assertEquals(3, hand.getHandSize());
    }
    @Test
    void chooseCard_IndexEqualsHandSize_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> hand.chooseCard(3));
        assertEquals(3, hand.getHandSize());
    }
    @Test
    void chooseCard_HandHasOneCard_ReturnsExplanation() {
        Hand oneCardHand = new Hand();
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
        Hand emptyHand = new Hand();

        assertThrows(IllegalArgumentException.class, () -> emptyHand.chooseCard(0));
        assertEquals(0, emptyHand.getHandSize());
    }

}
