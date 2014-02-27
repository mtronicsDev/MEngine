package mEngine.util.input;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private static boolean[] keyStates = new boolean[Keyboard.getKeyCount()];
    public static Map<String, Integer> keyAssignments = new HashMap<String, Integer>();

    public static boolean isKeyPressed(int key) { return Keyboard.isKeyDown(key); }

    public static boolean isKeyDown(int key) {

        boolean isAlreadyActivated = keyStates[key];
        keyStates[key] = isKeyPressed(key);
        return keyStates[key] != isAlreadyActivated && !isAlreadyActivated;

    }

    public static boolean isKeyUp(int key) {

        boolean isAlreadyActivated = keyStates[key];
        keyStates[key] = isKeyPressed(key);
        return keyStates[key] != isAlreadyActivated && isAlreadyActivated;

    }

    public static void assignKey(String key, int value) throws KeyAlreadyAssignedException {

        if(keyAssignments.get(key) != null) throw new KeyAlreadyAssignedException();

        else {

            keyAssignments.put(key, value);
            keyStates[value] = false;

        }

    }

    public static int getKey(String key) {

        return keyAssignments.get(key);

    }

    public static void unAssignKey(String key) { keyAssignments.remove(key); }

}

