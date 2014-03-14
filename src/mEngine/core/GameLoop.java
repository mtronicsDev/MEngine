package mEngine.core;

import mEngine.gameObjects.GameObject;
import mEngine.graphics.GraphicsController;
import mEngine.util.TimeHelper;
import mEngine.util.input.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector4f;

public class GameLoop {

    public static void loop() {

        while (!Display.isCloseRequested()) {

            GraphicsController.clearScreen(new Vector4f(0.44f, 0.58f, 0.93f, 1));

            if (Input.isKeyDown(Keyboard.KEY_ESCAPE)) {

                if (GameController.isGamePaused) GameController.unPauseGame();
                else GameController.pauseGame();

            }

            TimeHelper.updateDeltaTime();

            for (GameObject gameObject : ObjectController.gameObjects) {

                gameObject.update();

            }

            //--- Remove when not needed any longer ---
            //System resource usage
           /* ObjectController.getGUIElement(0, 0).getComponent("guiText").onExternalUpdate(new String[]{"mEngine Test Run @ " + FPS + " FPS"});
            ObjectController.getGUIElement(0, 1).getComponent("guiText").onExternalUpdate(new String[]{"MemUsage: "
                    + cutDecimals((float)getMemoryStats(USED_MEMORY) / getMemoryStats(TOTAL_MEMORY) * 100, 1)
                    + "% [" + getMemoryStats(TOTAL_MEMORY) + " MB]"});

            //Player stats
            ObjectController.getGUIElement(0, 2).getComponent("guiText").onExternalUpdate(new String[]{"x: " + cutDecimals(ObjectController.getGameObject(0).position.x, 7)});
            ObjectController.getGUIElement(0, 3).getComponent("guiText").onExternalUpdate(new String[]{"y: " + cutDecimals(ObjectController.getGameObject(0).position.y, 7)});
            ObjectController.getGUIElement(0, 4).getComponent("guiText").onExternalUpdate(new String[]{"z: " + cutDecimals(ObjectController.getGameObject(0).position.z, 7)});

            //Collider output
            ObjectController.getGUIElement(0, 5).getComponent("guiText").onExternalUpdate(new String[]{"Player collision: " + Collider.isCollidingWithSomething(ObjectController.getGameObject(0))});

            //RAM and FPS graphs
            GUIGraph graph = (GUIGraph)ObjectController.getGUIElement(0, 6).getComponent("guiGraphRAM");

            graph.onExternalUpdate(DataTypeHelper.doublePrimitiveToObject(
                    getMemoryGraph((int)graph.size.x).getValues()
            ));

            graph = (GUIGraph)ObjectController.getGUIElement(0, 6).getComponent("guiGraphFPS");

            graph.onExternalUpdate(DataTypeHelper.doublePrimitiveToObject(
                    getFPSGraph((int) graph.size.x).getValues()
            ));*/
            //--- End Remove ---

            TimeHelper.updateFPS();
            GraphicsController.update();

        }

        GameController.stopGame();

    }

}
