package mEngine.audio;

import mEngine.core.ObjectController;
import mEngine.interactive.gameObjects.GameObject;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;

public class AudioController {

    public static AudioListener listener;

    public static void initializeOpenAL() {

        try {

            AL.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public static void setListener(GameObject obj) {

        listener = new AudioListener(obj);

    }

    public static void close() {

        for(AudioSource source : ObjectController.audioSources) { source.close(); }
        AL.destroy();

    }

}
