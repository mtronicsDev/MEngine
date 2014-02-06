package mEngine.core;

import mEngine.audio.AudioSource;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIElement;
import mEngine.interactive.gui.GUIScreen;
import mEngine.interactive.gui.GUIText;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();
    public static List<AudioSource> audioSources = new ArrayList<AudioSource>();
    public static List<GUIScreen> guiScreens = new ArrayList<GUIScreen>();

    public static void addGameObject(GameObject object) { gameObjects.add(object); }
    public static void addAudioSource(AudioSource source) { audioSources.add(source); }
    public static void addGUIScreen(GUIScreen screen) { guiScreens.add(screen); }

    public static GameObject getGameObject(int index) { return gameObjects.get(index); }
    public static AudioSource getAudioSource(int index) { return audioSources.get(index); }
    public static GUIScreen getGUIScreen(int index) { return guiScreens.get(index); }

}
