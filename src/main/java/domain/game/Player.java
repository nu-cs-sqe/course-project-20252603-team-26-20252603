package domain.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Player {
    private static final String NAME_REQUIRED_MESSAGE = "name must not be blank";
    private static final String CARD_REQUIRED_MESSAGE = "card must not be null";
    private static final String INVALID_INDEX_MESSAGE = "card index must be between 0 and hand size - 1";

    private final String name;
    private final List<Card> hand;

    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NAME_REQUIRED_MESSAGE);
        }
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException(CARD_REQUIRED_MESSAGE);
        }
        hand.add(card);
    }

    public void removeCard(int index) {
        if (index < 0 || index >= getHandSize()){
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        hand.remove(index);
    }

    public int getHandSize() {
        return hand.size();
    }

    List<Card> getHandSnapshot() {
        return List.copyOf(hand);
    }

    long countCardsOfType(CardType type) {
        Objects.requireNonNull(type, "type must not be null");
        return hand.stream().filter(card -> card.getType() == type).count();
    }
}
