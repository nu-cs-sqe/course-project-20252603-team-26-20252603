package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class AttackCardControllerTest {
    @Test
    void play_AttackOnlyCard_ReturnsTwoTurnsAndDiscardsCard() {
        Player player = new Player("Brosef");
        Card attackCard = new Card(CardType.ATTACK);
        player.addCard(attackCard);
        Deck drawPile = new Deck(List.of(new Card(CardType.BEARD_CAT)));
        DiscardPile discardPile = new DiscardPile();
        AttackCardController controller = new AttackCardController(drawPile, discardPile);

        int forcedTurns = controller.play(player, 0);

        assertEquals(2, forcedTurns);
        assertEquals(0, player.getHandSize());
        assertEquals(List.of(attackCard), discardPile.snapshot());
    }
}
