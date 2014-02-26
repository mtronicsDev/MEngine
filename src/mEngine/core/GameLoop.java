package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIController;
import mEngine.interactive.gui.GUIScreen;
import mEngine.physics.Collider;
import mEngine.util.Input;
import mEngine.util.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

import static mEngine.util.NumberFormatHelper.cutDecimals;
import static mEngine.util.RuntimeHelper.*;
import static mEngine.util.TimeHelper.FPS;

public class GameLoop {

    public static void loop() {

        while (!Display.isCloseRequested()) {

            if(GUIController.isGUIActivated) GraphicsController.switchTo3D();
            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));

            if(Input.isKeyDown(Keyboard.KEY_ESCAPE)) {

                if(GameController.isGamePaused) GameController.unPauseGame();
                else GameController.pauseGame();

            }

            TimeHelper.updateDeltaTime();

            for(GameObject gameObject : ObjectController.gameObjects) {

                gameObject.update();

            }

            if(GUIController.isGUIActivated) GraphicsController.switchTo2D();

            if(GUIController.isGUIActivated)
                for(GUIScreen screen : ObjectController.guiScreens) { screen.update(); }

            //Remove when not needed any longer
            ObjectController.getGUIScreen(0).getGUIText(0).text = "mEngine Test Run @ " + FPS + " FPS";
            ObjectController.getGUIScreen(0).getGUIText(1).text = "MemUsage: " + cutDecimals((float)getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100, 1) + "% [" + getMemoryStats(TOTAL_MEMORY) + " MB]";
            ObjectController.getGUIScreen(0).getGUIText(2).text = "x: " + cutDecimals(ObjectController.getGameObject(0).position.x, 4);
            ObjectController.getGUIScreen(0).getGUIText(3).text = "y: " + cutDecimals(ObjectController.getGameObject(0).position.y, 4);
            ObjectController.getGUIScreen(0).getGUIText(4).text = "z: " + cutDecimals(ObjectController.getGameObject(0).position.z, 4);
            ObjectController.getGUIScreen(0).getGUIText(5).text = "Player collision: " + Collider.isCollidingWithSomething(ObjectController.getGameObject(0));

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
