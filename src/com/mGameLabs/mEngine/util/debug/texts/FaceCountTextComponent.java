package com.mGameLabs.mEngine.util.debug.texts;

import com.mGameLabs.mEngine.core.ObjectController;
import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.gameObjects.components.Component;
import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIText;
import com.mGameLabs.mEngine.gameObjects.components.renderable.Particle;
import com.mGameLabs.mEngine.gameObjects.components.renderable.RenderComponent;
import com.mGameLabs.mEngine.gameObjects.components.renderable.Skybox;
import com.mGameLabs.mEngine.gameObjects.components.renderable.Terrain;

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
