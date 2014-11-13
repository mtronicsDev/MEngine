/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.audio;

import com.polygame.engine.audio.AudioController;
import com.polygame.engine.gameObjects.modules.Module;
import com.polygame.engine.util.resources.ResourceHelper;
import paulscode.sound.SoundSystemConfig;

import java.util.Random;

public class AudioSource extends Module {

    private String sourceName;

    public AudioSource(String audioFileName, boolean isAmbient, boolean loop) {

        sourceName = audioFileName + new Random().nextInt();

        if (isAmbient) //Is the sound background music or a positional sound?
            AudioController.getSoundSystem().backgroundMusic(
              sourceName,
              ResourceHelper.getResourceURL(audioFileName, ResourceHelper.RES_SOUND),
              audioFileName + ".wav",
              loop);
        else
            AudioController.getSoundSystem().newSource(
              false,
              sourceName,
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
    }
}
