package mEngine.gameObjects.components;

import mEngine.util.PreferenceHelper;
import mEngine.util.input.Input;
import mEngine.util.input.KeyAlreadyAssignedException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static mEngine.util.input.Input.getKey;

public class ControllerKeyboardMouse extends Controller {

    public ControllerKeyboardMouse(float[] forceStrengths, boolean capableOfFlying) {

        super(forceStrengths, capableOfFlying);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

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

    public void updateObject() {

        MovementComponent movementComponent = (MovementComponent) parent.getComponent("movementComponent");

        if (movementComponent != null) {

            //Calculating the rotation
            float pitch = parent.rotation.x;
            float yaw = parent.rotation.y;

            float maxUpAngle = 90;
            float maxDownAngle = -90;

            float deltaMouseX = Mouse.getDX() * rotationSpeed;
            float deltaMouseY = Mouse.getDY() * rotationSpeed;

            if (yaw + deltaMouseX >= 360) {

                yaw = yaw + deltaMouseX - 360;

            } else if (yaw + deltaMouseX < 0) {

                yaw = 360 - yaw + deltaMouseX;

            } else {

                yaw += deltaMouseX;

            }

            if (pitch - deltaMouseY >= maxDownAngle && pitch - deltaMouseY <= maxUpAngle) {

                pitch += -deltaMouseY;

            } else if (pitch - deltaMouseY < maxDownAngle) {

                pitch = maxDownAngle;

            } else if (pitch - deltaMouseY > maxUpAngle) {

                pitch = maxUpAngle;

            }

            movementComponent.rotate(pitch, yaw);

            if (sprintModeToggle) {
                if (Input.isKeyDown(getKey("sprint"))) movementComponent.sprint();
            } else {
                if (Input.isKeyPressed(getKey("sprint"))) movementComponent.sprint();
            }

            if (sneakModeToggle) {
                if (Input.isKeyDown(getKey("sneak"))) movementComponent.sneak();
            } else {
                if (Input.isKeyPressed(getKey("sneak"))) movementComponent.sneak();
            }

            if (Input.isKeyPressed(getKey("forward"))) movementComponent.moveForward();
            if (Input.isKeyPressed(getKey("backward"))) movementComponent.moveBackward();
            if (Input.isKeyPressed(getKey("right"))) movementComponent.moveLeft();
            if (Input.isKeyPressed(getKey("left"))) movementComponent.moveRight();

            if (Input.isKeyPressed(getKey("up")) && capableOfFlying) movementComponent.moveUp();
            if (Input.isKeyPressed(getKey("down")) && capableOfFlying) movementComponent.moveDown();

            if (continuouslyJumping) {
                if (Input.isKeyPressed(getKey("jump"))) movementComponent.jump();
            } else {
                if (Input.isKeyDown(getKey("jump"))) movementComponent.jump();
            }

        }

    }

}
