package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObjectMovable;
import mEngine.util.Input;
import mEngine.util.KeyAlreadyAssignedException;
import mEngine.util.PreferenceHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static mEngine.util.Input.getKey;

public class KeyboardMouse extends Controller {

    public KeyboardMouse() {

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");

        try {

            Input.assignKey("forward", Keyboard.KEY_W);
            Input.assignKey("backward", Keyboard.KEY_S);
            Input.assignKey("right", Keyboard.KEY_D);
            Input.assignKey("left", Keyboard.KEY_A);

            Input.assignKey("up", Keyboard.KEY_E);
            Input.assignKey("down", Keyboard.KEY_Q);

            Input.assignKey("jump", Keyboard.KEY_SPACE);
            Input.assignKey("sprint", Keyboard.KEY_LSHIFT);
            Input.assignKey("sneak", Keyboard.KEY_C);

        } catch (KeyAlreadyAssignedException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public void updateObject(GameObjectMovable obj) {

        //Calculating the rotation
        float pitch = obj.rotation.x;
        float yaw = obj.rotation.y;

        final float MAX_UP_ANGLE = 90;
        final float MAX_DOWN_ANGLE = -90;

        float deltaMouseX = Mouse.getDX() * 0.16f;
        float deltaMouseY = Mouse.getDY() * 0.16f;

        if (yaw + deltaMouseX >= 360) {

            yaw = yaw + deltaMouseX - 360;

        } else if (yaw + deltaMouseX < 0) {

            yaw = 360 - yaw + deltaMouseX;

        } else {

            yaw += deltaMouseX;

        }

        if (pitch - deltaMouseY >= MAX_DOWN_ANGLE && pitch - deltaMouseY <= MAX_UP_ANGLE) {

            pitch += -deltaMouseY;

        } else if (pitch - deltaMouseY < MAX_DOWN_ANGLE) {

            pitch = MAX_DOWN_ANGLE;

        } else if (pitch - deltaMouseY > MAX_UP_ANGLE) {

            pitch = MAX_UP_ANGLE;

        }

        obj.rotate(pitch, yaw);

        if(Input.isKeyPressed(getKey("sprint"))) obj.sprint();
        if(sneakModeToggle) { if(Input.isKeyDown(getKey("sneak"))) obj.sneak(); }
        else { if(Input.isKeyPressed(getKey("sneak"))) obj.sneak(); }

        if(Input.isKeyPressed(getKey("forward"))) obj.moveForward();
        if(Input.isKeyPressed(getKey("backward"))) obj.moveBackward();
        if(Input.isKeyPressed(getKey("right"))) obj.moveLeft();
        if(Input.isKeyPressed(getKey("left"))) obj.moveRight();

        if(Input.isKeyPressed(getKey("up")) && obj.capableOfFlying) obj.moveUp();
        if(Input.isKeyPressed(getKey("down")) && obj.capableOfFlying) obj.moveDown();

        if(continuouslyJumping) { if(Input.isKeyPressed(getKey("jump"))) obj.jump(); }
        else { if(Input.isKeyDown(getKey("jump"))) obj.jump(); }

    }

}
