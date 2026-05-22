package domain.game;

import java.util.Objects;

public final class ExplodingKittenCardController {
    private static final String GAME_REQUIRED_MESSAGE = "game must not be null";

    private final Game game;

    public ExplodingKittenCardController(Game game) {
        this.game = Objects.requireNonNull(game, GAME_REQUIRED_MESSAGE);
    }
}
