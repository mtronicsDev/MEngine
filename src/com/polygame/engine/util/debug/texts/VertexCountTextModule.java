/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.util.debug.texts;

import com.polygame.engine.core.ObjectController;
import com.polygame.engine.gameObjects.GameObject;
import com.polygame.engine.gameObjects.modules.Module;
import com.polygame.engine.gameObjects.modules.gui.modules.GUIText;
import com.polygame.engine.gameObjects.modules.renderable.Particle;
import com.polygame.engine.gameObjects.modules.renderable.RenderModule;
import com.polygame.engine.gameObjects.modules.renderable.Skybox;
import com.polygame.engine.gameObjects.modules.renderable.Terrain;

public class VertexCountTextModule extends GUIText {

    public VertexCountTextModule(int fontSize) {
        super("VTX", fontSize);
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
