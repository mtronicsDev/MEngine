package mEngine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GraphicsController {

    public static int fps;

    public static void createDisplay(int width, int height, int fps, String title, boolean fullscreen) {

        GraphicsController.fps = fps;
        if(!fullscreen) { setupWindow(width, height, title); }

    }

    public static void update() {

        Display.update();
        Display.sync(fps);

    }

    private static void setupWindow(int width, int height, String title) {

        try {

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            Display.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

}
