package snorre.pong.game;

import java.util.ArrayList;
import java.util.List;

public class GameHistory implements GameHistoryObserver {
    private static final int MAX_HISTORY_SIZE = 5;  // Keep last 5 games
    private final List<GameResult> gameResults;

    public GameHistory() {
        this.gameResults = new ArrayList<>();
    }

    @Override
    public void onGameComplete(GameResult result) {
        if (gameResults.isEmpty() ||
            !gameResults.get(0).equals(result)) {
            gameResults.add(0, result);
            if (gameResults.size() > MAX_HISTORY_SIZE) {
                gameResults.remove(gameResults.size() - 1);
            }
        }
    }

    public List<GameResult> getRecentGames() {
        return new ArrayList<>(gameResults);  // Return copy to prevent modification
    }
}
