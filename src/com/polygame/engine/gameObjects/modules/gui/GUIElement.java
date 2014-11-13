/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.gui;

import com.polygame.engine.gameObjects.modules.gui.modules.GUIModule;
import com.polygame.engine.gameObjects.modules.renderable.ModuleRenderable;
import com.polygame.engine.graphics.Renderer;
import com.polygame.engine.graphics.gui.GUIScreen;
import com.polygame.engine.graphics.gui.GUIScreenController;
import com.polygame.engine.graphics.renderable.materials.Material2D;
import org.newdawn.slick.Color;

import javax.vecmath.Vector2f;
import java.util.ArrayList;
import java.util.List;

import static com.polygame.engine.graphics.GraphicsController.getHeight;
import static com.polygame.engine.graphics.GraphicsController.getWidth;

public class GUIElement extends ModuleRenderable {

    public Material2D material;
    public List<GUIModule> components = new ArrayList<GUIModule>();
    private Vector2f position; //Values from 0 to 1
    private Vector2f size; //Values from 0 to 1
    private GUIScreen screen; //The GUI screen to this element on

    public GUIElement(Vector2f posInPixels) {

        this(posInPixels, new Vector2f());

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels) {

        this(posInPixels, sizeInPixels, Color.white);

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, String textureName) {

        material = new Material2D();
        material.setTextureName(textureName);

        //Absolute position to relative position
        position = new Vector2f(
          posInPixels.x / getWidth(),
          posInPixels.y / getHeight()
        );

        //Absolute size to relative size
        size = new Vector2f(
          sizeInPixels.x / getWidth(),
          sizeInPixels.y / getHeight()
        );

    }

    public GUIElement(Vector2f posInPixels, Vector2f sizeInPixels, Color color) {

        material = new Material2D();
        material.setColor(new Color(color));

        //Absolute size to relative size
        position = new Vector2f();
        position.x = posInPixels.x / getWidth();
        position.y = posInPixels.y / getHeight();

        //Absolute size to relative size
        size = new Vector2f();
        size.x = sizeInPixels.x / getWidth();
        size.y = sizeInPixels.y / getHeight();

    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (GUIScreenController.isScreenActive(screen) || screen == null)
            for (GUIModule component : components)
                component.onUpdate();
    }

    @Override
    public void onSave() {

        super.onSave();
        for (GUIModule component : components) {

            component.onSave();

        }

        material.deleteTexture();
        material.deleteColor();

    }

    @Override
    public void onLoad() {

        super.onLoad();
        for (GUIModule component : components) {

            component.onLoad();

        }

    }

    public GUIElement setGUIScreen(GUIScreen screen) {
        this.screen = screen;
        return this;
    }

    public GUIElement addModule(GUIModule module) {

        components.add(module);
        module.onCreation(this);
        return this;

    }

    public GUIElement setMaterial(Material2D material) {
        this.material = material;
        return this;
    }

    public void render() {

        if (material.getTexture() == null && material.hasTexture())
            material.setTextureFromName();

        for (GUIModule component : components) {
            component.render();
        }

    }

    @Override
    public void addToRenderQueue() {

        if (GUIScreenController.isScreenActive(screen) || screen == null)
            Renderer.currentRenderQueue.addGUIElement(this);

    }

    public Vector2f getSize() {

        return new Vector2f(size.x * getWidth(), size.y * getHeight());

    }

    public void setSize(Vector2f sizeInPixels) {

        size = new Vector2f(sizeInPixels.x / getWidth(), sizeInPixels.y / getHeight());

    }

    public Vector2f getPosition() {

        return new Vector2f(position.x * getWidth(), position.y * getHeight());

    }

    public void setPosition(Vector2f positionInPixels) {

        position = new Vector2f(positionInPixels.x / getWidth(), positionInPixels.y / getHeight());

    }

}
