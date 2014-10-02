/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.audio;

import mEngine.audio.AudioController;
import mEngine.gameObjects.modules.Module;
import mEngine.util.input.Input;
import mEngine.util.resources.ResourceHelper;
import org.lwjgl.input.Keyboard;
import paulscode.sound.SoundSystemConfig;

public class AudioSource extends Module {

    private String sourceName;

    public AudioSource(String audioFileName, boolean isAmbient, boolean loop) {

        sourceName = audioFileName; //TODO: Generate unique id

        Input.assignKey("play", Keyboard.KEY_1);
        Input.assignKey("pause", Keyboard.KEY_2);
        Input.assignKey("stop", Keyboard.KEY_3);

        if (isAmbient) //Is the sound background music or a positional sound?
            AudioController.getSoundSystem().backgroundMusic(
              audioFileName,
              ResourceHelper.getResourceURL(audioFileName, ResourceHelper.RES_SOUND),
              audioFileName + ".wav",
              loop);
        else
            AudioController.getSoundSystem().newSource(
              false,
              audioFileName,
              ResourceHelper.getResourceURL(audioFileName, ResourceHelper.RES_SOUND),
              audioFileName + ".wav",
              false,
              0, 0, 0, //Parent is not accessible yet
              SoundSystemConfig.ATTENUATION_ROLLOFF,
              SoundSystemConfig.getDefaultRolloff()); // <-- Long constructor there :P
    }

    public void play() {
        AudioController.getSoundSystem().play(sourceName);
    }

    public void pause() {
        AudioController.getSoundSystem().pause(sourceName);
    }

    public void stop() {
        AudioController.getSoundSystem().stop(sourceName);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        AudioController.getSoundSystem().setPosition(sourceName,
          parent.position.x,
          parent.position.y,
          parent.position.z);

        if (!AudioController.getSoundSystem().playing(sourceName) && Input.isKeyDown("play")) play();
        if (AudioController.getSoundSystem().playing(sourceName) && Input.isKeyDown("pause")) pause();
        if (AudioController.getSoundSystem().playing(sourceName) && Input.isKeyDown("stop")) stop();
    }
}
