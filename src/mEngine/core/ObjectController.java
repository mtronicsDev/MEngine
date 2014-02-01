package mEngine.core;

import mEngine.audio.AudioSource;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIElement;
import mEngine.interactive.gui.GUIText;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();
    public static List<AudioSource> audioSources = new ArrayList<AudioSource>();
    public static List<GUIElement> guiElements = new ArrayList<GUIElement>();

    public static void addGameObject(GameObject object) { gameObjects.add(object); }
    public static void addAudioSource(AudioSource source) { audioSources.add(source); }
    public static void addGUIElement(GUIElement element) { guiElements.add(element); }

    public static GameObject getGameObject(int index) { return gameObjects.get(index); }
    public static AudioSource getAudioSource(int index) { return audioSources.get(index); }
    public static GUIElement getGUIElement(int index) { return guiElements.get(index); }
    public static GUIText getGUITextElement(int index) { return (GUIText)guiElements.get(index); }

}
