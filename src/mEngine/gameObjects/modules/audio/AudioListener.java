package mEngine.gameObjects.modules.audio;

import mEngine.audio.AudioController;
import mEngine.gameObjects.modules.Module;

public class AudioListener extends Module {

    @Override
    public void onUpdate() {
        super.onUpdate();
        AudioController.updateListener(this);
    }
}
