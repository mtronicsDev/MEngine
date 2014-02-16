package mEngine.interactive.controls;

import mEngine.interactive.components.Component;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.PreferenceHelper;

public class Gamepad extends Controller {

    public Gamepad() {

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        rotationSpeed = PreferenceHelper.getFloat("rotationSpeed");

    }

    public void updateObject(GameObject obj) {}

}
