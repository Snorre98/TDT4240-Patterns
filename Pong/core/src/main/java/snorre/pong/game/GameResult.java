package snorre.pong.game;


public class GameResult {
    private final String winner;
    private final int leftScore;
    private final int rightScore;
    private final long timestamp;

    public GameResult(String winner, int leftScore, int rightScore) {
        this.winner = winner;
        this.leftScore = leftScore;
        this.rightScore = rightScore;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameResult that = (GameResult) o;
        return timestamp == that.timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s won (%d-%d)", winner, leftScore, rightScore);
    }
}
