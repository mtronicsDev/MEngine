/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.core.events;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.glfw.WindowCallback;

import static com.polygame.engine.core.events.EventController.triggerEvent;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 13.11.2014 21:06
 */
public class WindowEventHandler extends WindowCallback {
    @Override
    public void windowPos(long window, int xpos, int ypos) {
        triggerEvent("windowMoved", xpos, ypos);
    }

    @Override
    public void windowSize(long window, int width, int height) {
        triggerEvent("windowResized", width, height);
    }

    @Override
    public void windowClose(long window) {
        triggerEvent("windowClosed");
    }

    @Override
    public void windowRefresh(long window) {
        triggerEvent("windowRefreshed");
    }

    @Override
    public void windowFocus(long window, int focused) {
        if (focused == GL11.GL_TRUE) triggerEvent("windowFocused");
        else triggerEvent("windowDefocused");
    }

    @Override
    public void windowIconify(long window, int iconified) {
        if (iconified == GL11.GL_TRUE) triggerEvent("windowIconified");
        else triggerEvent("windowDeiconofied");
    }

    @Override
    public void framebufferSize(long window, int width, int height) {
        triggerEvent("framebufferResized", width, height);
    }

    @Override
    public void key(long window, int key, int scancode, int action, int mods) {
        triggerEvent("keyPressed", key, scancode, action, mods);
    }

    @Override
    public void character(long window, int codepoint) {

    }

    @Override
    public void charMods(long window, int codepoint, int mods) {

    }

    @Override
    public void mouseButton(long window, int button, int action, int mods) {

    }

    @Override
    public void cursorPos(long window, double xpos, double ypos) {

    }

    @Override
    public void cursorEnter(long window, int entered) {
        if (entered == GL11.GL_TRUE) triggerEvent("cursorEntered");
        else triggerEvent("cursorLeft");
    }

    @Override
    public void scroll(long window, double xoffset, double yoffset) {

    }

    @Override
    public void drop(long window, int count, long names) {

    }
}
