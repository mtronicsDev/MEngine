package mEngine.graphics;

import mEngine.util.input.KeyAlreadyAssignedException;
import mEngine.util.resources.PreferenceHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector4f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static mEngine.util.input.Input.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class GraphicsController {

    public static Dimension currentRenderDimension;
    public static boolean isFullscreen;
    public static boolean mEnchmarkEnabled;
    public static int renderDistance;
    public static boolean isWireFrameMode = false;
    public static boolean isBlackAndWhite = false;
    public static boolean wasResized = false;
    public static int fieldOfView;
    private static int width;
    private static int height;
    private static String title;
    private static int fps;

    public static void createDisplay(String title) {

        width = PreferenceHelper.getInteger("screenWidth");
        height = PreferenceHelper.getInteger("screenHeight");
        renderDistance = PreferenceHelper.getInteger("renderDistance");
        fieldOfView = PreferenceHelper.getInteger("fieldOfView");

        fps = PreferenceHelper.getInteger("framesPerSecond");

        mEnchmarkEnabled = PreferenceHelper.getBoolean("mEnchmarkEnabled");
        GraphicsController.title = title;

        if (!PreferenceHelper.getBoolean("fullscreen")) {

            setupWindow(width, height, title);

        } else {

            setupFullscreen();

        }

        try {

            Mouse.create();

            assignKey("fullscreen", Keyboard.KEY_F11);

        } catch (KeyAlreadyAssignedException e) {

            e.printStackTrace();
            System.exit(1);

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        initializeOpenGL();

    }

    public static void clearScreen(Vector4f rgba) {

        glClearColor(rgba.x, rgba.y, rgba.z, rgba.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    public static void update() {

        if (getKey("fullscreen") != null) {

            if (isKeyDown(getKey("fullscreen"))) {

                wasResized = true;
                if (isFullscreen) setupWindow(width, height, title);
                else setupFullscreen();

            } else wasResized = false;

        }

        Display.update();
        if (!mEnchmarkEnabled) Display.sync(fps);

    }

    private static void setupWindow(int width, int height, String title) {

        try {

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            Display.setFullscreen(false);

            if (!Display.isCreated()) Display.create(new PixelFormat(8, 8, 0, 8));

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
            if (!Display.isCreated()) Display.create(new PixelFormat(8, 8, 0, 8));

        } catch (LWJGLException e) {

            e.printStackTrace();
            System.exit(1);

        }

        isFullscreen = true;

    }

    public static void setParent(Canvas parent) {

        try {
            Display.setParent(parent);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

    }

    public static void takeScreenshot() {

        int width = Display.getWidth();
        int height = Display.getHeight();
        int bytesPerPixel = 4; //1 byte per r/g/b/a value

        glReadBuffer(GL_FRONT);
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bytesPerPixel);
        glReadPixels(0, 0, width, height, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String saveFileName = format.format(date);

        File file = new File("screenshots/" + saveFileName + ".png");
        String fileFormat = "PNG";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                //Stuff 'n' things for every pixel
                int i = (x + (width * y)) * bytesPerPixel;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);

            }

        }

        try {
            ImageIO.write(image, fileFormat, file); //Write the file
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        glShadeModel(GL_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
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
            gluPerspective(fieldOfView, (float) Display.getWidth() / Display.getHeight(), 0.1f, renderDistance);
            glViewport(0, 0, Display.getWidth(), Display.getHeight());
            glMatrixMode(GL_MODELVIEW);

            currentRenderDimension = Dimension.DIM_3;

        }

    }

}
