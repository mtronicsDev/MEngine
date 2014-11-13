package com.polygame.engine.gameObjects.modules.controls;

import com.polygame.engine.gameObjects.modules.physics.MovementModule;
import com.polygame.engine.util.input.Input;
import com.polygame.engine.util.resources.PreferenceHelper;

public class ControllerManual extends Controller {

    int fovModifier = 2;

    public ControllerManual(float[] forceStrengths, boolean capableOfFlying) {

        super(forceStrengths, capableOfFlying);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

        Input.assignInputEvent("forward", Input.KEY_W);
        Input.assignInputEvent("backward", Input.KEY_S);
        Input.assignInputEvent("right", Input.KEY_D);
        Input.assignInputEvent("left", Input.KEY_A);

        Input.assignInputEvent("up", Input.KEY_E);
        Input.assignInputEvent("down", Input.KEY_Q);

        Input.assignInputEvent("jump", Input.KEY_SPACE);

        Input.assignInputEvent("sprint", Input.KEY_LEFT_SHIFT);

        Input.assignInputEvent("sneak", Input.KEY_C);

    }


    /**
     * update method for the game object the module is bound to
     */

    public void updateObject() {

        //TODO: check if there is a game pad plugged in

        MovementModule movementComponent = (MovementModule) parent.getModule(MovementModule.class);

        if (movementComponent != null) {

            //Calculating the rotation
            float pitch = parent.rotation.x;
            float yaw = parent.rotation.y;

            float maxUpAngle = 89.9f;
            float maxDownAngle = -89.9f;

            float deltaMouseX = 0; //TODO: Mouse.getDX() * rotationSpeed;
            float deltaMouseY = 0; //TODO: Mouse.getDY() * rotationSpeed;

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

            if (Input.isPressed("sprint")) movementComponent.sprint();
            if (Input.isPressed("sneak")) movementComponent.sneak();

            if (Input.isPressed("forward")) movementComponent.moveForward();
            if (Input.isPressed("backward")) movementComponent.moveBackward();
            if (Input.isPressed("right")) movementComponent.moveLeft();
            if (Input.isPressed("left")) movementComponent.moveRight();

            if (Input.isPressed("up") && capableOfFlying) movementComponent.moveUp();
            if (Input.isPressed("down") && capableOfFlying) movementComponent.moveDown();

            if (Input.isPressed("jump")) movementComponent.jump();

        }

    }

}
