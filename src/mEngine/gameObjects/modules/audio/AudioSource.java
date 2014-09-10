package mEngine.gameObjects.modules.audio;

import mEngine.MEngineException;
import mEngine.audio.AudioController;
import mEngine.gameObjects.modules.Module;

public class AudioSource extends Module {

    private String audioFileName;
    public float pitch;
    public float gain;

    public AudioSource(String audioFileName) {
        this(audioFileName, 1, 1);
    }

    public AudioSource(String audioFileName, float pitch, float gain) {
        this.audioFileName = audioFileName;
        this.pitch = pitch;
        this.gain = gain;

        try {
            AudioController.addSource(this);
        } catch (MEngineException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public String getAudioFileName() {
        return audioFileName;
    }

    public void play() {
        AudioController.play(this);
    }

    public void pause() {
        AudioController.pause(this);
    }

    public void stop() {
        AudioController.stop(this);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        AudioController.updateSource(this);
    }
}
