package mEngine.util.debug.texts;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.gameObjects.components.renderable.Particle;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.gameObjects.components.renderable.Skybox;
import mEngine.gameObjects.components.renderable.Terrain;

public class FaceCountTextComponent extends GUIText {

    public FaceCountTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int faceCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Component component : object.components) {

                if (component instanceof RenderComponent)
                    faceCount += ((RenderComponent) component).model.getFaces().size();

                else if (component instanceof Terrain) faceCount += ((Terrain) component).model.getFaces().size();

                else if (component instanceof Particle) faceCount++;

                else if (component instanceof Skybox) faceCount += 6;

            }

        }

        text = "faces: " + faceCount;

    }

}
