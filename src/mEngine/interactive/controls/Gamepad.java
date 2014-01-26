package mEngine.interactive.controls;

import mEngine.interactive.gameObjects.GameObjectMovable;
import mEngine.util.PreferenceHelper;

public class Gamepad extends Controller {

    public Gamepad() {

        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        sprintModeToggle = PreferenceHelper.getBoolean("sprintModeToggle");
        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");

    }

    public void updateObject(GameObjectMovable obj) {}

}
