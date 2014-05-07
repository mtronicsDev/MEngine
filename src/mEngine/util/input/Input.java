package mEngine.util.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public class Input {

    public static Map<String, Integer> keyAssignments = new HashMap<String, Integer>();
    private static boolean[] keyStats = new boolean[Keyboard.getKeyCount()];
    private static boolean[] buttonStats = new boolean[Mouse.getButtonCount()];

    public static boolean isKeyPressed(int key) {

        return Keyboard.isKeyDown(key);

    }

    public static boolean isButtonPressed(int key) {

        return Mouse.isButtonDown(key);

    }

    public static boolean isKeyDown(int key) {

        boolean isAlreadyActivated = keyStats[key];
        keyStats[key] = isKeyPressed(key);
        return keyStats[key] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isButtonDown(int key) {

        boolean isAlreadyActivated = buttonStats[key];
        keyStats[key] = isButtonPressed(key);
        return buttonStats[key] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isKeyUp(int key) {

        boolean isAlreadyActivated = keyStats[key];
        keyStats[key] = isKeyPressed(key);
        return keyStats[key] != isAlreadyActivated && isAlreadyActivated;

    }

    public static boolean isButtonUp(int key) {

        boolean isAlreadyActivated = buttonStats[key];
        keyStats[key] = isButtonPressed(key);
        return buttonStats[key] != isAlreadyActivated && isAlreadyActivated;

    }

    public static void assignKey(String key, int value) throws KeyAlreadyAssignedException {

        if (keyAssignments.get(key) != null) throw new KeyAlreadyAssignedException();

        else {

            keyAssignments.put(key, value);
            keyStats[value] = false;

        }

    }

    public static Integer getKey(String key) {

        Integer keyAssignment  = keyAssignments.get(key);

        if (keyAssignment != null) return keyAssignment;

        else return null;

    }

    public static void unAssignKey(String key) {
        keyAssignments.remove(key);
    }

}

