/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.graphics;

import com.polygame.engine.util.data.ColorHelper;
import com.polygame.engine.util.input.Input;
import com.polygame.engine.util.resources.PreferenceHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;

import javax.imageio.ImageIO;
import javax.vecmath.Vector4f;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.glfw.GLFW.*;

public class GraphicsController {

    public static Dimension currentRenderDimension;
    public static boolean isFullscreen;
    public static boolean mEnchmarkEnabled;
    public static int renderDistance;
    public static boolean isBlackAndWhite = false;
    public static boolean wasResized = false;
    public static int fieldOfView;
    public static Vector4f skyColor;

    private static int width;
    private static int height;
    private static String title;
    private static int fps;

    private static double nearPlane = 0.1, xPlane, yPlane; // farPlane = renderDistance

    /**
     * Creates a new window with OpenGL context, loads all graphics related preferences.
     */
    public static void createDisplay() {

        width = PreferenceHelper.getInteger("screenWidth");
        height = PreferenceHelper.getInteger("screenHeight");
        renderDistance = PreferenceHelper.getInteger("renderDistance");
        fieldOfView = PreferenceHelper.getInteger("fieldOfView");
        skyColor = ColorHelper.colorToRgba(ColorHelper.getHexColor(PreferenceHelper.getValue("skyColor")));

        fps = PreferenceHelper.getInteger("framesPerSecond");

        mEnchmarkEnabled = PreferenceHelper.getBoolean("mEnchmarkEnabled");
        title = PreferenceHelper.getValue("title");

        //TODO: Move to window resize callback
        yPlane = Math.tan(fieldOfView / 360 * Math.PI) * nearPlane;
        xPlane = yPlane * getAspectRatio();

        glfwInit();
        glfwDefaultWindowHints();
        setupWindow(width, height, title, PreferenceHelper.getBoolean("fullscreen"));
        initializeOpenGL();

    }

    /**
     * Used in the render loop, it clears the screen every frame and sets the sky color.
     */
    public static void clearScreen() {

        glClearColor(skyColor.x, skyColor.y, skyColor.z, skyColor.w);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    }

    /**
     * Called every frame by the render loop.
     * It detects, if the user has resized the window or wants to toggle fullscreen mode.
     * It also updates and synchronizes the window.
     */
    public static void update() {

        if (Input.isDown("fullscreen")) {
            wasResized = true;
            setupWindow(width, height, title, !isFullscreen);
        } else wasResized = false;

        //TODO: if (!mEnchmarkEnabled) Display.sync(fps);
        glfwSwapBuffers(1);

    }

    /**
     * Configures the window.
     *
     * @param width  Window width
     * @param height Window height
     * @param title  Window title
     */
    private static void setupWindow(int width, int height, String title, boolean fullscreen) {
        if (fullscreen) glfwCreateWindow(width, height, title, glfwGetPrimaryMonitor(), 0);
        else glfwCreateWindow(width, height, title, 0, 0);
    }

    public static boolean shouldClose() {
        return glfwWindowShouldClose(1) == 1;
    }

    /**
     * Takes a screenshot and saves it in the screenshots/ folder.
     */
    public static void takeScreenshot() {

        int width = getWidth();
        int height = getHeight();
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

    /**
     * Changes the title of the game window.
     *
     * @param title The desired title
     */
    public static void setWindowTitle(String title) {
        glfwSetWindowTitle(1, title);
    }

    /**
     * Returns the current game window width.
     *
     * @return The current game window width
     */
    public static int getWidth() {
        IntBuffer width = IntBuffer.allocate(1);
        IntBuffer height = IntBuffer.allocate(0);
        glfwGetWindowSize(1, width, height);
        return width.get();
    }

    /**
     * Returns the current game window height.
     *
     * @return The current game window height
     */
    public static int getHeight() {
        IntBuffer width = IntBuffer.allocate(0);
        IntBuffer height = IntBuffer.allocate(1);
        glfwGetWindowSize(1, width, height);
        return height.get();
    }

    /**
     * Returns the maximum frames per second specified in the properties.
     *
     * @return The maximum FPS value
     */
    public static int getFps() {
        return fps;
    }

    /**
     * Returns the game window's current aspect ratio.
     *
     * @return The game window's current aspect ratio
     */
    public static float getAspectRatio() {
        return (float) width / height;
    }

    /**
     * Configures OpenGL to look nice.
     */
    private static void initializeOpenGL() {

        glfwMakeContextCurrent(1);
        GLContext.createFromCurrent();

        glShadeModel(GL_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    }

    /**
     * Used for displaying 2D content, such as GUI.
     * Called in the render queue.
     */
    public static void switchTo2D() {

        if (currentRenderDimension != Dimension.DIM_2) {

            glMatrixMode(GL_MODELVIEW);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, getWidth(), getHeight(), 0, -1, 1);
            glViewport(0, 0, getWidth(), getHeight());
            glMatrixMode(GL_MODELVIEW);

            glPushMatrix();
            glLoadIdentity();

            currentRenderDimension = Dimension.DIM_2;

        }

    }

    /**
     * Used for displaying 3D content.
     * Called in the render queue.
     */
    public static void switchTo3D() {

        if (currentRenderDimension != Dimension.DIM_3) {

            glPopMatrix(); //From 2D
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glFrustum(-xPlane, xPlane, -yPlane, yPlane, nearPlane, renderDistance);
            glViewport(0, 0, getWidth(), getHeight());
            glMatrixMode(GL_MODELVIEW);

            currentRenderDimension = Dimension.DIM_3;

        }

    }

}
