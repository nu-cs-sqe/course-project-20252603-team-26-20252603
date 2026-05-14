package domain.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void play_AttackAtLastIndex_ReturnsTwoTurnsAndDiscardsOnlyAttackCard() {
        Player player = new Player("Brosef");
        Card catCard = new Card(CardType.BEARD_CAT);
        Card attackCard = new Card(CardType.ATTACK);
        player.addCard(catCard);
        player.addCard(attackCard);
        Deck drawPile = new Deck(List.of(new Card(CardType.BEARD_CAT)));
        DiscardPile discardPile = new DiscardPile();
        AttackCardController controller = new AttackCardController(drawPile, discardPile);

        int forcedTurns = controller.play(player, 1);

        assertEquals(2, forcedTurns);
        assertEquals(List.of(catCard), player.getHandSnapshot());
        assertEquals(List.of(attackCard), discardPile.snapshot());
    }

    @Test
    void play_NonAttackCard_ThrowsExceptionAndLeavesStateUnchanged() {
        Player player = new Player("Brosef");
        Card catCard = new Card(CardType.BEARD_CAT);
        player.addCard(catCard);
        Deck drawPile = new Deck(List.of(new Card(CardType.BEARD_CAT)));
        DiscardPile discardPile = new DiscardPile();
        AttackCardController controller = new AttackCardController(drawPile, discardPile);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> controller.play(player, 0));

        assertEquals("selected card is not an attack card", exception.getMessage());
        assertEquals(List.of(catCard), player.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }

    @Test
    void play_NegativeCardIndex_ThrowsExceptionAndLeavesStateUnchanged() {
        Player player = new Player("Brosef");
        Card attackCard = new Card(CardType.ATTACK);
        player.addCard(attackCard);
        Deck drawPile = new Deck(List.of(new Card(CardType.BEARD_CAT)));
        DiscardPile discardPile = new DiscardPile();
        AttackCardController controller = new AttackCardController(drawPile, discardPile);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> controller.play(player, -1));

        assertEquals("cardIndex is out of bounds", exception.getMessage());
        assertEquals(List.of(attackCard), player.getHandSnapshot());
        assertEquals(List.of(), discardPile.snapshot());
    }
}
