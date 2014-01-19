package mEngine.core;

import mEngine.audio.AudioSource;
import mEngine.interactive.gameObjects.Camera;
import mEngine.interactive.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> objects = new ArrayList<GameObject>();
    public static List<AudioSource> audioSources = new ArrayList<AudioSource>();
    public static Camera camera;

    public static void addObject(GameObject object) { objects.add(object); }

    public static void addAudioSource(AudioSource source) { audioSources.add(source); }

}
