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
        hand.addCard(new Card(CardType.OTHER));
    }
}
