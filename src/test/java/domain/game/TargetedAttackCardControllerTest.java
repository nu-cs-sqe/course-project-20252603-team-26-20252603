package domain.game;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void play_ValidCard_RemovesFromHandAndAddsToDiscard() {
        Player player = new Player("Sophie");
        Card targetedAttack = new Card(CardType.TARGETED_ATTACK);
        player.addCard(targetedAttack);
        DiscardPile discardPile = new DiscardPile();
        TargetedAttackCardController controller = new TargetedAttackCardController(discardPile);

        controller.play(player, 0);

        assertEquals(0, player.getHandSize());
        assertEquals(1, discardPile.size());
        assertEquals(List.of(targetedAttack), discardPile.snapshot());
    }

}

