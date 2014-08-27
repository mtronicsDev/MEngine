package com.mGameLabs.mEngine.gameObjects.components.gui.guiComponents;

import com.mGameLabs.mEngine.gameObjects.components.gui.GUIElement;
import com.mGameLabs.mEngine.graphics.GraphicsController;
import org.lwjgl.util.vector.Vector2f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GUIComponent implements Serializable {

    public GUIElement parent;
    protected List<Vector2f> verticesToRender;

    public GUIComponent() {

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
