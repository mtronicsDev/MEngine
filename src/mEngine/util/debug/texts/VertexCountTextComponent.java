package mEngine.util.debug.texts;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.gui.modules.GUIText;
import mEngine.gameObjects.modules.renderable.Particle;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.gameObjects.modules.renderable.Skybox;
import mEngine.gameObjects.modules.renderable.Terrain;

public class VertexCountTextComponent extends GUIText {

    public VertexCountTextComponent(String text, int fontSize) {

        super(text, fontSize);

    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int vertexCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Module module : object.modules) {

                if (module instanceof RenderModule)
                    vertexCount += ((RenderModule) module).model.getVertices().size() * 3;

                else if (module instanceof Terrain)
                    vertexCount += ((Terrain) module).model.getVertices().size() * 3;

                else if (module instanceof Particle) vertexCount += 4;

                else if (module instanceof Skybox) vertexCount += 24;

            }

        }

        text = "vertices: " + vertexCount;

    }

}
