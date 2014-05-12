package mEngine.util.debug.texts;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.gameObjects.components.renderable.Particle;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.gameObjects.components.renderable.Skybox;
import mEngine.gameObjects.components.renderable.Terrain;

public class VertexCountTextComponent extends GUIText {

    public VertexCountTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int vertexCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Component component : object.components.values()) {

                if (component instanceof RenderComponent)
                    vertexCount += ((RenderComponent) component).model.vertices.size();

                else if (component instanceof Terrain) vertexCount += ((Terrain) component).model.vertices.size();

                else if (component instanceof Particle) vertexCount += 4;

                else if (component instanceof Skybox) vertexCount += 8;

            }

        }

        text = "vertices: " + vertexCount;

    }

}
