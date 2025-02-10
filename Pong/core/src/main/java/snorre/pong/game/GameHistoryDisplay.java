package snorre.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameHistoryDisplay {

    private final SpriteBatch batch;
    private final BitmapFont font;
    private final GameHistory gameHistory;

    public GameHistoryDisplay(SpriteBatch batch, BitmapFont font, GameHistory gameHistory) {
        this.batch = batch;
        this.font = font;
        this.gameHistory = gameHistory;
    }

    public void render() {
        float y = Gdx.graphics.getHeight() - 150;  // Start below score
        font.draw(batch, "Recent Games:", 10, y);
        y -= 30;

        for (GameResult result : gameHistory.getRecentGames()) {
            font.draw(batch, result.toString(), 10, y);
            y -= 25;
        }
    }
}
