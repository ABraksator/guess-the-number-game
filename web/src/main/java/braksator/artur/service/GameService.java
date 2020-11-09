package braksator.artur.service;

import braksator.artur.Game;

public interface GameService {

    boolean isGameOver();

    String getMainMessage();

    String getResultMessage();

    void checkGuess(int guess);

    void reset();

    Game getGame();

}
