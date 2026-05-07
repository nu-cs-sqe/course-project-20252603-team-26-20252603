package domain.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Player {
    private static final String NAME_REQUIRED_MESSAGE = "name must not be blank";
    private static final String CARD_REQUIRED_MESSAGE = "card must not be null";
    private static final String INVALID_INDEX_MESSAGE = "cardIndex is out of bounds";
    private static final String DEFUSE_DESCRIPTION = "Defuse: Use this card to avoid exploding after drawing an Exploding Kitten.";
    private static final String PLACEHOLDER_DESCRIPTION = "Placeholder Card: This card is choosable, but its effect will be implemented later.";
    private static final String CAT_CARD_DESCRIPTION = "Cat Card: This card is powerless on its own. Play two matching Cat Cards as a pair to steal a random card from another player.";
    private static final String NOT_CHOOSE_MESSAGE = "This card type cannot be chosen from a player's hand";

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
        if (index < 0 || index >= getHandSize()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        hand.remove(index);
    }

    public boolean removedCardByType(CardType type){
        // TODO: implement a function that tests whether the size of card type has changed
        return true;
    }

    public int getHandSize() {
        return hand.size();
    }

    public String chooseCard(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }
        Card chosenCard = hand.get(cardIndex);

        if (chosenCard.getType() == CardType.DEFUSE) {
            return DEFUSE_DESCRIPTION;
        }

        if (chosenCard.getType() == CardType.PLACEHOLDER_CARD) {
            return PLACEHOLDER_DESCRIPTION;
        }
        if (isCatCard(chosenCard.getType())) {
            return CAT_CARD_DESCRIPTION;
        }

        throw new IllegalStateException(NOT_CHOOSE_MESSAGE);

    }

    List<Card> getHandSnapshot() {
        return List.copyOf(hand);
    }

    long countCardsOfType(CardType type) {
        Objects.requireNonNull(type, "type must not be null");
        return hand.stream().filter(card -> card.getType() == type).count();
    }

    public boolean canSubmitCard(int cardIndex) {
        if (cardIndex < 0 || cardIndex >= hand.size()) {
            throw new IllegalArgumentException(INVALID_INDEX_MESSAGE);
        }

        Card chosenCard = hand.get(cardIndex);
        CardType chosenType = chosenCard.getType();

        if (isCatCard(chosenType)) {
            return countCardsOfType(chosenType) >= 2;
        }

        if (chosenType == CardType.PLACEHOLDER_CARD) {
            return true;
        }

        return false;
    }
    private boolean isCatCard(CardType type) {
        return type == CardType.BEARD_CAT
                || type == CardType.HAIRY_POTATO_CAT
                || type == CardType.TACOCAT
                || type == CardType.RAINBOW_RALPHING_CAT;
    }
}
