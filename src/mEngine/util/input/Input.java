package mEngine.util.input;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private static boolean[] keyStats = new boolean[Keyboard.getKeyCount()];
    public static Map<String, Integer> keyAssignments = new HashMap<String, Integer>();

    public static boolean isKeyPressed(int key) { return Keyboard.isKeyDown(key); }

    public static boolean isKeyDown(int key) {

        boolean isAlreadyActivated = keyStats[key];
        keyStats[key] = isKeyPressed(key);
        return keyStats[key] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isKeyUp(int key) {

        boolean isAlreadyActivated = keyStats[key];
        keyStats[key] = isKeyPressed(key);
        return keyStats[key] != isAlreadyActivated && isAlreadyActivated;

    }

    public static void assignKey(String key, int value) throws KeyAlreadyAssignedException {

        if(keyAssignments.get(key) != null) throw new KeyAlreadyAssignedException();

        else {

            keyAssignments.put(key, value);
            keyStats[value] = false;

        }

    }

    public static int getKey(String key) {

        return keyAssignments.get(key);

    }

    public static void unAssignKey(String key) { keyAssignments.remove(key); }

}

