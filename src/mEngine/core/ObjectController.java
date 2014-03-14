package mEngine.core;

import mEngine.audio.AudioSource;
import mEngine.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();
    public static List<AudioSource> audioSources = new ArrayList<AudioSource>();

    public static void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public static void addAudioSource(AudioSource source) {
        audioSources.add(source);
    }

    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

    public static AudioSource getAudioSource(int index) {
        return audioSources.get(index);
    }

}
