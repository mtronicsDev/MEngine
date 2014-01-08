package mEngine.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GraphicsController {

    private static int width;
    private static int height;

    private static int fps;

    public static void createDisplay(int width, int height, int fps, String title, boolean fullscreen) {

        GraphicsController.width = width;
        GraphicsController.height = height;

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

    public static int getWidth() { return width; }
    public static int getHeight() { return height; }

    public static int getFps() { return fps; }

    public static float getAspectRatio() { return (float)width / height; }

}
