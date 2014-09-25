/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.gameObjects.modules.gui.modules.buttons;

import mEngine.gameObjects.modules.gui.modules.GUIModule;
import mEngine.util.input.Input;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class GUIButton extends GUIModule {

    //Later used for rising edge / falling edge detection
    private boolean buttonPressed;
    private boolean buttonHovered;
    private boolean previouslyPressed;
    private ButtonPressAction buttonPressAction;

    public GUIButton(ButtonPressAction buttonPressAction) {

        this.buttonPressAction = buttonPressAction;
        previouslyPressed = false;

    }

    public void onUpdate() {

        super.onUpdate();
        buttonHovered = isButtonHovered();
        buttonPressed = isButtonPressed();

        if (buttonHovered) buttonPressAction.hovered();
        if (buttonPressed) buttonPressAction.pressed();
        if (isButtonReleased()) buttonPressAction.up();
        if (isButtonActivated()) buttonPressAction.down();

        previouslyPressed = buttonPressed;

    }

    private boolean isButtonHovered() {

        buttonPressAction.hovered();
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
