package snorre.pong.game;

import snorre.pong.game.GameResult;

public interface GameHistoryObserver {
    void onGameComplete(GameResult result);
}
