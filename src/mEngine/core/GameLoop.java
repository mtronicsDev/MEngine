package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIController;
import mEngine.interactive.gui.GUIScreen;
import mEngine.util.Input;
import mEngine.util.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

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
            ObjectController.getGUIScreen(0).getGUIText(2).text = "x: " + String.valueOf(ObjectController.getGameObject(0).position.x);
            ObjectController.getGUIScreen(0).getGUIText(3).text = "y: " + String.valueOf(ObjectController.getGameObject(0).position.y);
            ObjectController.getGUIScreen(0).getGUIText(4).text = "z: " + String.valueOf(ObjectController.getGameObject(0).position.z);

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
