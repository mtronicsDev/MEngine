package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector2f;

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

    public void checkInputKeys(GameObject obj) {

        if(Input.isKeyDown(keys[0])) obj.moveForward();
        if(Input.isKeyDown(keys[1])) obj.moveBackward();
        if(Input.isKeyDown(keys[2])) obj.moveLeft();
        if(Input.isKeyDown(keys[3])) obj.moveRight();
        if(Input.isKeyDown(keys[4])) obj.moveDown();
        if(Input.isKeyDown(keys[5])) obj.moveUp();
        if(Input.isKeyDown(keys[6])) obj.sprint();
        if(Input.isKeyDown(keys[7])) obj.sneak();
        if(Input.isKeyDown(keys[8])) obj.jump();

    }

    //Not needed
    public void actIntelligently(GameObject obj) {}

}
