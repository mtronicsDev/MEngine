/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.gui.modules.buttons;

import com.polygame.engine.core.events.EventHandler;
import com.polygame.engine.gameObjects.modules.gui.modules.GUIModule;
import com.polygame.engine.util.input.Input;

import java.util.Random;

import static com.polygame.engine.core.events.EventController.addEventHandler;
import static com.polygame.engine.core.events.EventController.triggerEvent;

public class GUIButton extends GUIModule {

    private boolean buttonHovered;
    private boolean previouslyPressed = false;
    private boolean previouslyHovered = false;
    private int id; //For event ids to be unique
    private boolean[] triggerEnabled = new boolean[6];

    public GUIButton() {
        id = new Random().nextInt();
    }

    public void onUpdate() {

        super.onUpdate();
        buttonHovered = isButtonHovered();
        boolean buttonPressed = isButtonPressed();

        if (buttonPressed && triggerEnabled[0]) triggerEvent(id + "-pressed");
        if (isButtonDown() && triggerEnabled[1]) triggerEvent(id + "-down");
        if (isButtonUp() && triggerEnabled[2]) triggerEvent(id + "-up");
        if (buttonHovered && triggerEnabled[3]) triggerEvent(id + "-hovered");
        if (isButtonEntered() && triggerEnabled[4]) triggerEvent(id + "-entered");
        if (isButtonExited() && triggerEnabled[5]) triggerEvent(id + "-exited");

        previouslyPressed = buttonPressed;
        previouslyHovered = buttonHovered;

    }

    public GUIButton setEventHandler(ButtonEvent trigger, EventHandler handler) {
        triggerEnabled[trigger.ordinal()] = true;
        String event = id + "-" + trigger.name;

        addEventHandler(event, handler);

        return this;
    }

    private boolean isButtonHovered() {

        return /*Mouse.getX() >= parent.getPosition().x && Mouse.getX() <= parent.getPosition().x + parent.getSize().x &&
          Display.getHeight() - Mouse.getY() >= parent.getPosition().y && Display.getHeight() - Mouse.getY() <= parent.getPosition().y + parent.getSize().y;*/
          true; //TODO: Fix this!

    }

    private boolean isButtonEntered() {
        return isButtonHovered() && !previouslyHovered;
    }

    private boolean isButtonExited() {
        return !isButtonHovered() && previouslyHovered;
    }

    private boolean isButtonPressed() {

        return Input.isPressed("") && buttonHovered;

    }

    private boolean isButtonUp() {

        return Input.isUp("") && previouslyPressed;

    }

    private boolean isButtonDown() {

        return isButtonPressed() && !previouslyPressed;

    }

    public enum ButtonEvent {
        PRESSED("pressed"),
        DOWN("down"),
        UP("up"),

        HOVERED("hovered"),
        ENTERED("entered"),
        EXITED("exited");

        private String name;

        private ButtonEvent(String name) {
            this.name = name;
        }

    }

}
