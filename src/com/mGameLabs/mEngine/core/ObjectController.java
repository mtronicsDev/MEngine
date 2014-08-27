package com.mGameLabs.mEngine.core;

import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.graphics.renderable.LoadingScreen;

import java.util.ArrayList;
import java.util.List;

public class ObjectController {

    public static List<GameObject> gameObjects = new ArrayList<GameObject>();
    public static int activeGUIDepartment;
    public static int activeMenuGUIDepartment;
    public static int maxMenuGUIDepartments;
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
