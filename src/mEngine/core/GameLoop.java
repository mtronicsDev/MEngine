package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIController;
import mEngine.interactive.gui.GUIElement;
import mEngine.interactive.gui.GUIScreen;
import mEngine.util.Input;
import mEngine.util.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;

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
            ObjectController.getGUIScreen(0).getGUIText(1).text = "MemUsage: [Total: " + getMemoryStats(TOTAL_MEMORY) +
                    " MB | Used: " + getMemoryStats(USED_MEMORY) +
                    " MB | Free: " + getMemoryStats(FREE_MEMORY) + " MB]";

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
