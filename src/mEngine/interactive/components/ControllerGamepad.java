package mEngine.interactive.components;

import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.PreferenceHelper;

public class ControllerGamepad extends Controller {

    public ControllerGamepad(float[] forceStrengths) {

        super(forceStrengths);

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

    }

    public void updateObject(GameObject obj) {}

}
