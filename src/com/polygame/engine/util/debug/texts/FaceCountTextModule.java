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

public class FaceCountTextModule extends GUIText {

    public FaceCountTextModule(int fontSize) {
        super("FAC", fontSize);
    }

    @Override
    public void onUpdate() {

        super.onUpdate();

        int faceCount = 0;

        for (GameObject object : ObjectController.gameObjects) {

            for (Module module : object.modules) {

                if (module instanceof RenderModule)
                    faceCount += ((RenderModule) module).model.getFaces().size();
                else if (module instanceof Terrain) faceCount += ((Terrain) module).model.getFaces().size();
                else if (module instanceof Particle) faceCount++;
                else if (module instanceof Skybox) faceCount += 6;

            }

        }

        text = "faces: " + faceCount;

    }

}
