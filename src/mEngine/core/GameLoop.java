package mEngine.core;

import mEngine.graphics.GraphicsController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gui.GUIController;
import mEngine.interactive.gui.GUIElement;
import mEngine.util.Input;
import mEngine.util.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class GameLoop {

    public static void loop() {

        while (!Display.isCloseRequested()) {

            if(GUIController.isGUIActivated) GraphicsController.switchTo3D();
            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));

            if(Input.isKeyDown(Keyboard.KEY_ESCAPE)) {

                if(GameController.isGamePaused) GameController.unPauseGame();
                else GameController.pauseGame();

            }

            for(GameObject gameObject : ObjectController.gameObjects) {

                gameObject.update();

            }

            ObjectController.camera.update();

            if(GUIController.isGUIActivated) GraphicsController.switchTo2D();

            if(GUIController.isGUIActivated)
                for(GUIElement guiElement : ObjectController.guiElements)
                    guiElement.update();

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
