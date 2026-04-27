package domain.game;

import java.util.ArrayList;
import java.util.List;

public final class DiscardPile {
    private static final String CARD_REQUIRED_MESSAGE = "card must not be null";

    private final List<Card> cards;

    public DiscardPile() {
        this.cards = new ArrayList<>();
    }

    public void add(Card card) {
        if (card == null) {
            throw new IllegalArgumentException(CARD_REQUIRED_MESSAGE);
        }
        cards.add(card);
    }

    int size() {
        return cards.size();
    }

    List<Card> snapshot() {
        return List.copyOf(cards);
    }
}
