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

}
