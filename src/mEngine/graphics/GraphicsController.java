package mEngine.graphics;

import mEngine.util.PreferenceHelper;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GraphicsController {

    private static int width;
    private static int height;

    private static int fps;
    public static boolean isFullscreen;
    public static boolean is3DActive;

    public static void createDisplay(int fps, String title) {

        GraphicsController.width = PreferenceHelper.getInteger("screenWidth");
        GraphicsController.height = PreferenceHelper.getInteger("screenHeight");

        GraphicsController.fps = fps;

        if(!PreferenceHelper.getBoolean("fullscreen")) {

            setupWindow(width, height, title);
            isFullscreen = false;

        }
        else {

            setupFullscreen();
            isFullscreen = true;

        }

    }

    public static void clearScreen(Vector4f rgba) {

        glClearColor(rgba.x, rgba.y, rgba.z, rgba.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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

    private static void setupFullscreen() {

        try {

            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(true);
            Display.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

    }

    public static void setWindowTitle(String title) { Display.setTitle(title); }

    public static int getWidth() { return width; }
    public static int getHeight() { return height; }

    public static int getFps() { return fps; }

    public static float getAspectRatio() { return (float)width / height; }

    public static void switchTo2D() {
        
        glMatrixMode(GL_PROJECTION);
        gluOrtho2D(0, Display.getWidth(), 0, Display.getHeight());
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_MODELVIEW);
        glDisable(GL_DEPTH_TEST);
        //glPushMatrix();
        glColor3f(1, 0, 1);
        glBegin(GL_LINES);
        glVertex2f(100, 100);
        glVertex2f(300, 100);
        glVertex2f(300, 300);

        glVertex2f(300, 100);
        glVertex2f(300, 300);
        glVertex2f(100, 300);
        glEnd();
        //glPopMatrix();

    }

    public static void switchTo3D() {

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(PreferenceHelper.getInteger("fieldOfView"), (float) Display.getWidth() / Display.getHeight(), 0.1f, 1000);
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_DEPTH_TEST);

    }

}
