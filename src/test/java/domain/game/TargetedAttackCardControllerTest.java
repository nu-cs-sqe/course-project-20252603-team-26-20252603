package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TargetedAttackCardControllerTest {

    @Test
    void play_NegativeIndex_ThrowsIllegalArgumentException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.TARGETED_ATTACK));
        DiscardPile discardPile = new DiscardPile();
        TargetedAttackCardController controller = new TargetedAttackCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, -1));
    }

    @Test
    void play_IndexEqualsHandSize_ThrowsIllegalArgumentException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.TARGETED_ATTACK));
        DiscardPile discardPile = new DiscardPile();
        TargetedAttackCardController controller = new TargetedAttackCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, player.getHandSize()));
    }

    @Test
    void play_NotTargetedAttackCard_ThrowsIllegalArgumentException() {
        Player player = new Player("Sophie");
        player.addCard(new Card(CardType.SKIP));
        DiscardPile discardPile = new DiscardPile();
        TargetedAttackCardController controller = new TargetedAttackCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, 0));
    }

}

