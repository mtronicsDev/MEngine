/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.util.input;

import mEngine.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public class Input {

    private static Map<String, Integer> keyAssignments;
    private static boolean[] keyStates;
    private static boolean[] buttonStates;

    /**
     * Initializes the state arrays and the map of key assignments
     */
    public static void initialize() {
        keyAssignments = new HashMap<>();
        keyStates = new boolean[Keyboard.getKeyCount()];
        buttonStates = new boolean[(int) MathHelper.clampMin(Mouse.getButtonCount(), 3)];
    }

    /**
     * Tells you if a certain key is currently in the "pressed" or "down" state
     *
     * @param key The desired key, has to be assigned via assignKey() first
     * @return True if the key is pressed, false if not
     */
    public static boolean isKeyPressed(String key) {

        return Keyboard.isKeyDown(keyAssignments.get(key));

    }

    /**
     * Tells you if a certain mouse button is currently in the "pressed" or "down" state
     *
     * @param button The desired mouse button
     * @return True if the button is pressed, false if not
     */
    public static boolean isButtonPressed(int button) {

        return Mouse.isButtonDown(button);

    }

    /**
     * Tells you if a certain key was just pressed down
     *
     * @param key The desired key, has to be assigned via assignKey() first
     * @return True if the key was just pressed down, false if not
     */
    public static boolean isKeyDown(String key) {

        boolean isAlreadyActivated = keyStates[keyAssignments.get(key)];
        keyStates[keyAssignments.get(key)] = isKeyPressed(key);
        return keyStates[keyAssignments.get(key)] != isAlreadyActivated && !isAlreadyActivated;

    }

    /**
     * Tells you if a certain mouse button was just pressed down
     *
     * @param button The desired mouse button
     * @return True if the button was just pressed down, false if not
     */
    public static boolean isButtonDown(int button) {

        boolean isAlreadyActivated = buttonStates[button];
        buttonStates[button] = isButtonPressed(button);
        return buttonStates[button] != isAlreadyActivated && !isAlreadyActivated;

    }

    /**
     * Tells you if a certain key was just released
     *
     * @param key The desired key, has to be assigned via assignKey() first
     * @return True if the key was just released, false if not
     */
    public static boolean isKeyUp(String key) {

        boolean isAlreadyActivated = keyStates[keyAssignments.get(key)];
        keyStates[keyAssignments.get(key)] = isKeyPressed(key);
        return keyStates[keyAssignments.get(key)] != isAlreadyActivated && isAlreadyActivated;

    }

    /**
     * Tells you if a certain mouse button was just released
     *
     * @param button The desired mouse button
     * @return True if the button was just released, false if not
     */
    public static boolean isButtonUp(int button) {

        boolean isAlreadyActivated = buttonStates[button];
        buttonStates[button] = isButtonPressed(button);
        return buttonStates[button] != isAlreadyActivated && isAlreadyActivated;

    }

    /**
     * Assign a name to a specific key (e.g. "forward" to Keyboard.KEY_W)
     *
     * @param name    The name of the key, e.g. "forward"
     * @param keycode The keycode, usually Keyboard.KEY_x where x is the desired key
     */
    public static void assignKey(String name, int keycode) {
        keyAssignments.put(name, keycode);
        keyStates[keycode] = false;
    }

    /**
     * Releases the desired key from its binding
     *
     * @param name The name of the key, e.g. "forward"
     */
    public static void unAssignKey(String name) {
        keyAssignments.remove(name);
    }

}

