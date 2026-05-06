package domain.game;

import java.util.Objects;

public class Card {
    private final CardType type;
    //hi

    public Card(CardType type) {
    this.type = Objects.requireNonNull(type, "type must not be null");
}

    public CardType getType() {
        return type;
    }
}
