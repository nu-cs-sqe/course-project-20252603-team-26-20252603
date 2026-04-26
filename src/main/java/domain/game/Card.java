package domain.game;

import java.util.Objects;

class Card {
    private final CardType type;

    Card(CardType type) {
    this.type = Objects.requireNonNull(type, "type must not be null");
}

    CardType getType() {
        return type;
    }
}
