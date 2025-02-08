package snorre.pong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pong extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont gameOverFont;
    private GlyphLayout layout;

    // Game objects
    private Rectangle paddleLeft;
    private Rectangle paddleRight;
    private Rectangle ball;
    private Vector2 ballVelocity;

    // Constants
    private static final float PADDLE_WIDTH = 20;
    private static final float PADDLE_HEIGHT = 100;
    private static final float BALL_SIZE = 20;
    private static final float PADDLE_SPEED = 400;
    private static final float BALL_SPEED = 400;
    private static final int WINNING_SCORE = 21;

    // Game state
    private boolean isGameOver = false;
    private String winner = "";

    // Scores
    private int scoreLeft = 0;
    private int scoreRight = 0;

    private InputManager inputManager;

    @Override
    public void create() {
        inputManager = InputManager.getInstance();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        gameOverFont = new BitmapFont();
        layout = new GlyphLayout();

        font.getData().setScale(2);
        gameOverFont.getData().setScale(3);

        float screenHeight = Gdx.graphics.getHeight();
        paddleLeft = new Rectangle(50, screenHeight/2 - PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddleRight = new Rectangle(Gdx.graphics.getWidth() - 50 - PADDLE_WIDTH, screenHeight/2 - PADDLE_HEIGHT/2, PADDLE_WIDTH, PADDLE_HEIGHT);

        resetBall();
    }

    private void resetBall() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        ball = new Rectangle(screenWidth/2 - BALL_SIZE/2, screenHeight/2 - BALL_SIZE/2, BALL_SIZE, BALL_SIZE);

        // Random initial direction
        float angle = (float)(Math.random() * Math.PI/4 + Math.PI/8);
        if (Math.random() > 0.5f) angle += (float) Math.PI;

        ballVelocity = new Vector2((float)Math.cos(angle) * BALL_SPEED, (float)Math.sin(angle) * BALL_SPEED);
    }

    @Override
    public void render() {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!isGameOver) {
            // Update game logic
            update(Gdx.graphics.getDeltaTime());
        }

        // Draw game objects
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);

        // Draw paddles
        shapeRenderer.rect(paddleLeft.x, paddleLeft.y, paddleLeft.width, paddleLeft.height);
        shapeRenderer.rect(paddleRight.x, paddleRight.y, paddleRight.width, paddleRight.height);

        // Draw ball if game is not over
        if (!isGameOver) {
            shapeRenderer.rect(ball.x, ball.y, ball.width, ball.height);
        }

        shapeRenderer.end();

        // Draw scores and game over message
        batch.begin();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Left score
        font.draw(batch, String.valueOf(scoreLeft), screenWidth/4, screenHeight - 50);

        // Right score
        font.draw(batch, String.valueOf(scoreRight), 3 * screenWidth/4, screenHeight - 50);

        // Draw game over message if game is over
        if (isGameOver) {
            drawGameOverGraphic(screenWidth, screenHeight);
        }

        batch.end();
    }

    private void drawGameOverGraphic(float screenWidth, float screenHeight){
        String gameOverText = "GAME OVER\n" + winner + " WINS!";
        layout.setText(gameOverFont, gameOverText);
        float textX = (screenWidth - layout.width) / 2;
        float textY = (screenHeight + layout.height) / 2;
        gameOverFont.draw(batch, gameOverText, textX, textY);

        // Draw restart instruction
        String restartText = "Press SPACE to play again";
        layout.setText(font, restartText);
        float restartX = (screenWidth - layout.width) / 2;
        font.draw(batch, restartText, restartX, textY - 120);

        // Check for restart
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            restartGame();
        }
    }

    private void restartGame() {
        scoreLeft = 0;
        scoreRight = 0;
        isGameOver = false;
        winner = "";
        resetBall();
    }

    public void ballPaddleCollision() {
        ballVelocity.x = -ballVelocity.x * 1.1f; // Increase speed slightly
        // Add some vertical velocity based on where the ball hits the paddle
        Rectangle paddle = ball.overlaps(paddleLeft) ? paddleLeft : paddleRight;
        float relativeIntersectY = (paddle.y + (paddle.height/2)) - (ball.y + (ball.height/2));
        float normalizedRelativeIntersectY = relativeIntersectY/(paddle.height/2);
        float bounceAngle = (float)(normalizedRelativeIntersectY * (5*Math.PI/12));
        ballVelocity.y = (float)Math.sin(bounceAngle) * BALL_SPEED;
    }

    public void scoring(float screenWidth) {
        if (ball.x + ball.width < 0) {
            scoreRight++;
            setWinner();
            if (!isGameOver) resetBall();
        } else if (ball.x > screenWidth) {
            scoreLeft++;
            setWinner();
            if (!isGameOver) resetBall();
        }
    }

    private void setWinner() {
        if (scoreLeft >= WINNING_SCORE) {
            isGameOver = true;
            winner = "Left Player";
        } else if (scoreRight >= WINNING_SCORE) {
            isGameOver = true;
            winner = "Right Player";
        }
    }

    private void update(float deltaTime) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        inputManager.update();

        if (inputManager.isPlayerTwoUp()) {
            paddleLeft.y += PADDLE_SPEED * deltaTime;
        }
        if (inputManager.isPlayerTwoDown()) {
            paddleLeft.y -= PADDLE_SPEED * deltaTime;
        }
        if (inputManager.isPlayerOneUp()) {
            paddleRight.y += PADDLE_SPEED * deltaTime;
        }
        if (inputManager.isPlayerOneDown()) {
            paddleRight.y -= PADDLE_SPEED * deltaTime;
        }

        // Keep paddles within screen bounds
        paddleLeft.y = Math.max(0, Math.min(paddleLeft.y, screenHeight - paddleLeft.height));
        paddleRight.y = Math.max(0, Math.min(paddleRight.y, screenHeight - paddleRight.height));

        // Update ball position
        ball.x += ballVelocity.x * deltaTime;
        ball.y += ballVelocity.y * deltaTime;

        // Ball collision with top and bottom
        if (ball.y <= 0 || ball.y + ball.height >= screenHeight) {
            ballVelocity.y = -ballVelocity.y;
        }

        // Ball collision with paddles
        if (ball.overlaps(paddleLeft) || ball.overlaps(paddleRight)) {
            ballPaddleCollision();
        }

        scoring(screenWidth);
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
        gameOverFont.dispose();
    }
}
