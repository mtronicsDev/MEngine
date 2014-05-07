package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.renderable.LoadingScreen;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();
    private static LoadingScreen loadingScreen;

    public static void addGameObject(GameObject object) {
        gameObjects.add(object);
    }

    public static GameObject getGameObject(int index) {
        return gameObjects.get(index);
    }

    public static LoadingScreen getLoadingScreen() {
        return loadingScreen;
    }

    public static void setLoadingScreen(LoadingScreen screen) {
        loadingScreen = screen;
    }

}
