package mEngine.util.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private static Map<String, Integer> keyAssignments = new HashMap<String, Integer>();
    private static boolean[] keyStats = new boolean[Keyboard.getKeyCount()];
    private static boolean[] buttonStats = new boolean[Mouse.getButtonCount()];

    public static boolean isKeyPressed(String key) {

        return Keyboard.isKeyDown(keyAssignments.get(key));

    }

    public static boolean isButtonPressed(int key) {

        return Mouse.isButtonDown(key);

    }

    public static boolean isKeyDown(String key) {

        boolean isAlreadyActivated = keyStats[keyAssignments.get(key)];
        keyStats[keyAssignments.get(key)] = isKeyPressed(key);
        return keyStats[keyAssignments.get(key)] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isButtonDown(int key) {

        boolean isAlreadyActivated = buttonStats[key];
        buttonStats[key] = isButtonPressed(key);
        return buttonStats[key] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isKeyUp(String key) {

        boolean isAlreadyActivated = keyStats[keyAssignments.get(key)];
        keyStats[keyAssignments.get(key)] = isKeyPressed(key);
        return keyStats[keyAssignments.get(key)] != isAlreadyActivated && isAlreadyActivated;

    }

    public static boolean isButtonUp(int key) {

        boolean isAlreadyActivated = buttonStats[key];
        buttonStats[key] = isButtonPressed(key);
        return buttonStats[key] != isAlreadyActivated && isAlreadyActivated;

    }

    public static void assignKey(String key, int value) {
        keyAssignments.put(key, value);
        keyStats[value] = false;
    }

    public static void unAssignKey(String key) {
        keyAssignments.remove(key);
    }

}

