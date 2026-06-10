package domain.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class DrawFromBottomCardController {
    private static final String NOT_DRAW_FROM_BOTTOM_CARD_MESSAGE = "selected card is not a Draw from bottom card";
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private final Deck deck;
    private final DiscardPile discardPile;


    @SuppressFBWarnings("EI")
    public DrawFromBottomCardController(Deck deck, DiscardPile discardPile) {
        this.discardPile = discardPile;
        this.deck = deck;
    }

    public Card play(Player player, int cardIndex) {
        if (cardIndex < 0 || cardIndex >= player.getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }

        Card selectedCard = player.getHandSnapshot().get(cardIndex);

        if (selectedCard.getType() != CardType.DRAW_FROM_BOTTOM) {
            throw new IllegalArgumentException(NOT_DRAW_FROM_BOTTOM_CARD_MESSAGE);
        }

        player.removeCard(cardIndex);
        discardPile.add(selectedCard);
        Card pulledCard = deck.drawFromBottom();
        player.addCard(pulledCard);
        return pulledCard;

    }

}
