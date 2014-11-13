/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.gui.modules;

import com.polygame.engine.gameObjects.modules.gui.GUIElement;
import com.polygame.engine.graphics.GraphicsController;

import javax.vecmath.Vector2f;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GUIModule implements Serializable {

    public GUIElement parent;
    protected List<Vector2f> verticesToRender;

    public GUIModule() {

        verticesToRender = new ArrayList<Vector2f>();

    }

    public void onCreation(GUIElement element) {
        parent = element;
    }

    public void onUpdate() {
    }

    public void onDestroy() {
    }

    public void onRemoteUpdate() {
    }

    public void onExternalUpdate(Object[] args) {
    }

    public void onSave() {
    }

    public void onLoad() {
    }

    public void render() {

        if (GraphicsController.wasResized) verticesToRender = new ArrayList<Vector2f>();

    }

}
