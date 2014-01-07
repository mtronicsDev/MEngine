package mEngine.util;

import org.lwjgl.input.Keyboard;

public class Input {

    public static int forward = Keyboard.KEY_W;
    public static int backward = Keyboard.KEY_S;

    public static int left = Keyboard.KEY_A;
    public static int right = Keyboard.KEY_D;

    public static int up = Keyboard.KEY_E;
    public static int down = Keyboard.KEY_Q;

    public static int sprint = Keyboard.KEY_LSHIFT;
    public static int sneak = Keyboard.KEY_C;

    public static int jump = Keyboard.KEY_SPACE;

    public static int endGame = Keyboard.KEY_ESCAPE;
    public static int pauseGame = Keyboard.KEY_TAB;


    public static boolean isKeyPressed(int keyCode) {

        return Keyboard.isKeyDown(keyCode);

    }

}
