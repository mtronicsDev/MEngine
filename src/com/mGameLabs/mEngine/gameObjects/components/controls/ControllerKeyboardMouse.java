package com.mGameLabs.mEngine.gameObjects.components.controls;

import com.mGameLabs.mEngine.gameObjects.components.physics.MovementComponent;
import com.mGameLabs.mEngine.graphics.GraphicsController;
import com.mGameLabs.mEngine.util.input.Input;
import com.mGameLabs.mEngine.util.input.KeyAlreadyAssignedException;
import com.mGameLabs.mEngine.util.resources.PreferenceHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import static com.mGameLabs.mEngine.util.input.Input.getKey;

public class ControllerKeyboardMouse extends Controller {

    int fovModifier = 2;

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

        if (Mouse.isButtonDown(1)) {

            int change = Mouse.getDWheel();

            if (change > 0) fovModifier++;

            else if (change < 0 && fovModifier > 2) fovModifier--;

            GraphicsController.fieldOfView = PreferenceHelper.getInteger("fieldOfView") / fovModifier;
            rotationSpeed = 0.02f;

        } else {

            GraphicsController.fieldOfView = PreferenceHelper.getInteger("fieldOfView");
            rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

        }

        MovementComponent movementComponent = (MovementComponent) parent.getAnyComponent(MovementComponent.class);

        if (movementComponent != null) {

            //Calculating the rotation
            float pitch = parent.rotation.x;
            float yaw = parent.rotation.y;

            float maxUpAngle = 89.9f;
            float maxDownAngle = -89.9f;

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

            if (getKey("forward") != null) {

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

}
