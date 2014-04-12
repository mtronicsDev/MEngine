package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.audio.AudioSource;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();
    public static List<AudioSource> audioSources = new ArrayList<AudioSource>();

    public static void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

    public static void removeGameObject(int index) {
        gameObjects.remove(index);
    }

    public static void addAudioSource(AudioSource source) {
        audioSources.add(source);
    }

    public static AudioSource getAudioSource(int index) {
        return audioSources.get(index);
    }

}
