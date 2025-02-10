package snorre.pong.game;
import com.badlogic.gdx.math.Rectangle;
public class SessionStateManager {

    private static final int WINNING_SCORE = 21;
    private int leftScore;
    private int rightScore;
    private boolean isGameOver;
    private String winner;

    private boolean startNewRound;

    private static SessionStateManager instance;

    private SessionStateManager(){
        leftScore = 0;
        rightScore = 0;
        isGameOver = false;
        winner = "";
    }

    public static synchronized SessionStateManager getInstance(){
        if(instance == null){
            instance = new SessionStateManager();
        }
        return instance;
    }



    public void updateSessionState(float screenWidth, Rectangle ball){
        startNewRound = false;
        if(ball.x + ball.width <0){
            incrementRightScore();
            checkWinner();
            if(!isGameOver){
                setStartNewRound();
            }
        } else if (ball.x > screenWidth){
            incrementLeftScore();
            checkWinner();
            if(!isGameOver){
                setStartNewRound();
            }
        }
    }


    /*
    #####################
     Helper functions
    #####################
    */


    private void checkWinner(){
        if (leftScore >= WINNING_SCORE) {
            setGameOver();
            setWinner("Left Player");
        } else if (rightScore >= WINNING_SCORE) {
            setGameOver();
            setWinner("Right Player");
        }
    }

    private void setStartNewRound(){
        startNewRound = true;
    }

    private void setGameOver(){
        isGameOver = true;
    }

    private void incrementRightScore(){
        rightScore++;
    }

    private void incrementLeftScore(){
        leftScore++;
    }

    private void setWinner(String newWinner){
        winner = newWinner;
    }

    public void resetSessionState(){
        leftScore = 0;
        rightScore = 0;
        isGameOver = false;
        winner = "";
        setStartNewRound();
    }
    public String getWinner(){
        return winner;
    }
    public boolean isGameOver(){
        return isGameOver;
    }

    public int getLeftScore(){
        return leftScore;
    }

    public int getRightScore(){
        return rightScore;
    }

    public boolean getStartNewRound(){
        return startNewRound;
    }


}
