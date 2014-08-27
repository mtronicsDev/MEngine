package com.mGameLabs.mEngine.util.debug.texts;

import com.mGameLabs.mEngine.core.ObjectController;
import com.mGameLabs.mEngine.gameObjects.GameObject;
import com.mGameLabs.mEngine.gameObjects.components.Component;
import com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents.GUIText;
import com.mGameLabs.mEngine.gameObjects.components.renderable.Particle;
import com.mGameLabs.mEngine.gameObjects.components.renderable.RenderComponent;
import com.mGameLabs.mEngine.gameObjects.components.renderable.Skybox;
import com.mGameLabs.mEngine.gameObjects.components.renderable.Terrain;

public class VertexCountTextComponent extends GUIText {

    public VertexCountTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int vertexCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Component component : object.components) {

                if (component instanceof RenderComponent)
                    vertexCount += ((RenderComponent) component).model.getVertices().size() * 3;

                else if (component instanceof Terrain)
                    vertexCount += ((Terrain) component).model.getVertices().size() * 3;

                else if (component instanceof Particle) vertexCount += 4;

                else if (component instanceof Skybox) vertexCount += 24;

            }

        }

        text = "vertices: " + vertexCount;

    }

}
