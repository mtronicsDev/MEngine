package mEngine.graphics;

import mEngine.util.PreferenceHelper;
import mEngine.util.input.KeyAlreadyAssignedException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector4f;

import static mEngine.util.input.Input.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GraphicsController {

    public static Dimension currentRenderDimension;
    public static boolean isFullscreen;
    public static boolean mEnchmarkEnabled;
    private static int width;
    private static int height;
    private static String title;
    private static int fps;

    public static void createDisplay(String title) {

        width = PreferenceHelper.getInteger("screenWidth");
        height = PreferenceHelper.getInteger("screenHeight");

        fps = PreferenceHelper.getInteger("framesPerSecond");

        mEnchmarkEnabled = PreferenceHelper.getBoolean("mEnchmarkEnabled");
        GraphicsController.title = title;

        try {

            assignKey("fullscreen", Keyboard.KEY_F11);

        } catch (KeyAlreadyAssignedException e) {

            e.printStackTrace();
            System.exit(1);

        }

        if (!PreferenceHelper.getBoolean("fullscreen")) {

            setupWindow(width, height, title);

        } else {

            setupFullscreen();

        }

        initializeOpenGL();

    }

    public static void clearScreen(Vector4f rgba) {

        glClearColor(rgba.x, rgba.y, rgba.z, rgba.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    public static void update() {

        if (isKeyDown(getKey("fullscreen"))) {

            if (isFullscreen) setupWindow(width, height, title);
            else setupFullscreen();

        }

        Display.update();
        if (!mEnchmarkEnabled) Display.sync(fps);

    }

    private static void setupWindow(int width, int height, String title) {

        try {

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            Display.setFullscreen(false);

            if (!Display.isCreated()) Display.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        isFullscreen = false;

    }

    private static void setupFullscreen() {

        try {

            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setFullscreen(true);
            if (!Display.isCreated()) Display.create();

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        isFullscreen = true;

    }

    public static void setWindowTitle(String title) {
        Display.setTitle(title);
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static int getFps() {
        return fps;
    }

    public static float getAspectRatio() {
        return (float) width / height;
    }

    private static void initializeOpenGL() {

        //Enabling OpenGL functions
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        //Using them
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    public static void switchTo2D() {

        if (currentRenderDimension != Dimension.DIM_2) {

            glMatrixMode(GL_MODELVIEW);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glMatrixMode(GL_MODELVIEW);

            glPushMatrix();
            glLoadIdentity();

            currentRenderDimension = Dimension.DIM_2;

        }

    }

    public static void switchTo3D() {

        if (currentRenderDimension != Dimension.DIM_3) {

            glPopMatrix(); //From 2D
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            gluPerspective(PreferenceHelper.getInteger("fieldOfView"), (float) Display.getWidth() / Display.getHeight(), 0.1f, 1000);
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glMatrixMode(GL_MODELVIEW);

            currentRenderDimension = Dimension.DIM_3;

        }

    }

}
