/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.gui.modules.buttons;

import mEngine.core.events.EventHandler;
import mEngine.gameObjects.modules.gui.modules.GUIModule;
import mEngine.util.input.Input;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.Random;

import static mEngine.core.events.EventController.*;

public class GUIButton extends GUIModule {

    private boolean buttonHovered;
    private boolean previouslyPressed = false;
    private int id; //For event ids to be unique
    private boolean[] eventHandlersEnabled = new boolean[4];

    public GUIButton(EventHandler down) {
        id = new Random().nextInt();
        addEvent(id + "-down");
        addEventHandler(id + "-down", down);
        eventHandlersEnabled[0] = true;
    }

    public GUIButton(EventHandler down, EventHandler up, EventHandler pressed, EventHandler hovered) {
        id = new Random().nextInt();
        addEvent(id + "-down");
        addEvent(id + "-up");
        addEvent(id + "-pressed");
        addEvent(id + "-hovered");

        addEventHandler(id + "-down", down);
        addEventHandler(id + "-up", up);
        addEventHandler(id + "-pressed", pressed);
        addEventHandler(id + "-hovered", hovered);

        for (int i = 0; i < eventHandlersEnabled.length; i++) {
            eventHandlersEnabled[i] = true;
        }
    }

    public void onUpdate() {

        super.onUpdate();
        buttonHovered = isButtonHovered();
        boolean buttonPressed = isButtonPressed();

        if (buttonHovered) if (eventHandlersEnabled[3]) triggerEvent(id + "-hovered");
        if (buttonPressed) if (eventHandlersEnabled[2]) triggerEvent(id + "-pressed");
        if (isButtonReleased()) if (eventHandlersEnabled[1]) triggerEvent(id + "-up");
        if (isButtonActivated()) if (eventHandlersEnabled[0]) triggerEvent(id + "-down");

        previouslyPressed = buttonPressed;

    }

    private boolean isButtonHovered() {

        return Mouse.getX() >= parent.getPosition().x && Mouse.getX() <= parent.getPosition().x + parent.getSize().x &&
          Display.getHeight() - Mouse.getY() >= parent.getPosition().y && Display.getHeight() - Mouse.getY() <= parent.getPosition().y + parent.getSize().y;

    }

    private boolean isButtonPressed() {

        return Input.isButtonPressed(0) && buttonHovered;

    }

    private boolean isButtonReleased() {

        return Input.isButtonUp(0) && previouslyPressed;

    }

    private boolean isButtonActivated() {

        return isButtonPressed() && !previouslyPressed;

    }

}
