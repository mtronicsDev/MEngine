package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObjectMovable;
import mEngine.util.Input;
import mEngine.util.KeyAlreadyAssignedException;
import org.lwjgl.input.Keyboard;

import static mEngine.util.Input.getKey;

public class KeyboardMouse extends Controller {

    public int[] keys = new int[Keyboard.getKeyCount()];

    public KeyboardMouse() {

        try {

            Input.assignKey("forward", Keyboard.KEY_W);
            Input.assignKey("backward", Keyboard.KEY_S);
            Input.assignKey("right", Keyboard.KEY_D);
            Input.assignKey("left", Keyboard.KEY_A);

            Input.assignKey("up", Keyboard.KEY_E);
            Input.assignKey("down", Keyboard.KEY_Q);

            Input.assignKey("jump", Keyboard.KEY_SPACE);
            Input.assignKey("sprint", Keyboard.KEY_LCONTROL);
            Input.assignKey("sneak", Keyboard.KEY_LSHIFT);

        } catch (KeyAlreadyAssignedException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public void updateObject(GameObjectMovable obj) {

        if(Input.isKeyPressed(getKey("forward"))) obj.moveForward();
        if(Input.isKeyPressed(getKey("backward"))) obj.moveBackward();
        if(Input.isKeyPressed(getKey("right"))) obj.moveLeft();
        if(Input.isKeyPressed(getKey("left"))) obj.moveRight();

        if(Input.isKeyPressed(getKey("up"))) obj.moveUp();
        if(Input.isKeyPressed(getKey("down"))) obj.moveDown();

        if(Input.isKeyPressed(getKey("jump"))) obj.jump();
        if(Input.isKeyPressed(getKey("sprint"))) obj.sprint();
        if(Input.isKeyPressed(getKey("sneak"))) obj.sneak();

    }

}
