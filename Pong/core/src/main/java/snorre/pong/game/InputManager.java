package snorre.pong.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputManager {
    private static InputManager instance;
    private boolean isPlayerOneUp;
    private boolean isPlayerOneDown;

    private boolean isPlayerTwoUp;
    private boolean isPlayerTwoDown;

    private InputManager(){
        isPlayerOneUp = false;
        isPlayerOneDown = false;

        isPlayerTwoUp = false;
        isPlayerTwoDown = false;
    }

    public static InputManager getInstance() {
        if(instance == null){
            instance = new InputManager();
        }
        return instance;
    }

    public void update(){
        isPlayerOneUp = Gdx.input.isKeyPressed(Input.Keys.UP);
        isPlayerOneDown = Gdx.input.isKeyPressed(Input.Keys.DOWN);

        isPlayerTwoUp = Gdx.input.isKeyPressed(Input.Keys.W);
        isPlayerTwoDown = Gdx.input.isKeyPressed(Input.Keys.S);
    }

    public boolean isPlayerOneUp(){
        return isPlayerOneUp;
    }

    public boolean isPlayerOneDown() {
        return isPlayerOneDown;
    }

    public boolean isPlayerTwoUp() {
        return isPlayerTwoUp;
    }

    public boolean isPlayerTwoDown(){
        return isPlayerTwoDown;
    }
}
