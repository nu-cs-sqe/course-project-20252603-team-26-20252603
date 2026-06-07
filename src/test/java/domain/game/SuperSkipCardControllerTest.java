package domain.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SuperSkipCardControllerTest {
    @Test
    void playSuperSkip_NonSuperSkipCard_ThrowsException() {
        Player player = new Player("Gojo");
        player.addCard(new Card(CardType.ATTACK));

        DiscardPile discardPile = new DiscardPile();
        SuperSkipCardController controller = new SuperSkipCardController(discardPile);

        assertThrows(IllegalArgumentException.class, () -> controller.play(player, 0));
    }

}
