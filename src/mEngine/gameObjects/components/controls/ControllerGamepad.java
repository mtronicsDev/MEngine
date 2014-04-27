package mEngine.gameObjects.components.controls;

import mEngine.util.resources.PreferenceHelper;

public class ControllerGamepad extends Controller {

    public ControllerGamepad(float[] forceStrengths, boolean capableOfFlying) {

        this(forceStrengths, capableOfFlying, false);

    }

    public ControllerGamepad(float[] forceStrengths, boolean capableOfFlying, boolean addedAsLast) {

        super(forceStrengths, capableOfFlying, addedAsLast);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

    }

    public void updateObject() {
    }

}
