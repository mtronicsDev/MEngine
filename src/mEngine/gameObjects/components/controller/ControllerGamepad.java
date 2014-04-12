package mEngine.gameObjects.components.controller;

import mEngine.gameObjects.components.controller.Controller;
import mEngine.util.preferences.PreferenceHelper;

import java.io.Serializable;

public class ControllerGamepad extends Controller implements Serializable {

    public ControllerGamepad(float[] forceStrengths, boolean capableOfFlying) {

        super(forceStrengths, capableOfFlying);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

    }

    public void updateObject() {
    }

}
