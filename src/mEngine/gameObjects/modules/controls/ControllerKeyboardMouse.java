/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.controls;

import mEngine.gameObjects.modules.physics.MovementModule;
import mEngine.graphics.GraphicsController;
import mEngine.util.input.Input;
import mEngine.util.resources.PreferenceHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ControllerKeyboardMouse extends Controller {

    int fovModifier = 2;

    public ControllerKeyboardMouse(float[] forceStrengths, boolean capableOfFlying) {

        super(forceStrengths, capableOfFlying);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

        Input.assignKey("forward", Keyboard.KEY_W);
        Input.assignKey("backward", Keyboard.KEY_S);
        Input.assignKey("right", Keyboard.KEY_D);
        Input.assignKey("left", Keyboard.KEY_A);

        Input.assignKey("up", Keyboard.KEY_E);
        Input.assignKey("down", Keyboard.KEY_Q);

        Input.assignKey("jump", Keyboard.KEY_SPACE);
        Input.assignKey("sprint", Keyboard.KEY_LSHIFT);
        Input.assignKey("sneak", Keyboard.KEY_C);

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

        MovementModule movementComponent = (MovementModule) parent.getModule(MovementModule.class);

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

            if (sprintModeToggle) {
                if (Input.isKeyDown("sprint")) movementComponent.sprint();
            } else {
                if (Input.isKeyPressed("sprint")) movementComponent.sprint();
            }

            if (sneakModeToggle) {
                if (Input.isKeyDown("sneak")) movementComponent.sneak();
            } else {
                if (Input.isKeyPressed("sneak")) movementComponent.sneak();
            }

            if (Input.isKeyPressed("forward")) movementComponent.moveForward();
            if (Input.isKeyPressed("backward")) movementComponent.moveBackward();
            if (Input.isKeyPressed("right")) movementComponent.moveLeft();
            if (Input.isKeyPressed("left")) movementComponent.moveRight();

            if (Input.isKeyPressed("up") && capableOfFlying) movementComponent.moveUp();
            if (Input.isKeyPressed("down") && capableOfFlying) movementComponent.moveDown();

            if (continuouslyJumping) {
                if (Input.isKeyPressed("jump")) movementComponent.jump();
            } else {
                if (Input.isKeyDown("jump")) movementComponent.jump();
            }

        }

    }

}
