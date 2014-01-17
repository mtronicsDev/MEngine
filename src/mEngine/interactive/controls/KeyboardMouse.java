package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObjectMovable;
import mEngine.util.Input;
import org.lwjgl.input.Keyboard;

public class KeyboardMouse extends Controller {

    public int[] keys = new int[Keyboard.getKeyCount()];

    public KeyboardMouse() {

        keys[0] = Keyboard.KEY_W;
        keys[1] = Keyboard.KEY_S;
        keys[2] = Keyboard.KEY_A;
        keys[3] = Keyboard.KEY_D;
        keys[4] = Keyboard.KEY_Q;
        keys[5] = Keyboard.KEY_E;
        keys[6] = Keyboard.KEY_LSHIFT;
        keys[7] = Keyboard.KEY_C;
        keys[8] = Keyboard.KEY_SPACE;

    }

    public void updateObject(GameObjectMovable obj) {

        if(Input.isKeyPressed(keys[0])) obj.moveForward();
        if(Input.isKeyPressed(keys[1])) obj.moveBackward();
        if(Input.isKeyPressed(keys[2])) obj.moveLeft();
        if(Input.isKeyPressed(keys[3])) obj.moveRight();
        if(Input.isKeyPressed(keys[4])) obj.moveDown();
        if(Input.isKeyPressed(keys[5])) obj.moveUp();
        if(Input.isKeyPressed(keys[6])) obj.sprint();
        if(Input.isKeyPressed(keys[7])) obj.sneak();
        if(Input.isKeyPressed(keys[8])) obj.jump();

    }

}
