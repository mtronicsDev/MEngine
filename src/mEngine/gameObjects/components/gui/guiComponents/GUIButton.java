package mEngine.gameObjects.components.gui.guiComponents;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.io.Serializable;

public class GUIButton extends GUIComponent implements Serializable {

    //Later used for rising edge / falling edge detection
    boolean isButtonPressed;
    boolean isButtonHovered;

    public GUIButton() {
    }

    public void onUpdate() {

        super.onUpdate();
        isButtonPressed = buttonPressed();
        isButtonHovered = buttonHovered();

    }

    public boolean buttonHovered() {

        return Mouse.getX() >= parent.position.x && Mouse.getX() <= parent.position.x + parent.size.x &&
                Display.getHeight() - Mouse.getY() >= parent.position.y && Display.getHeight() - Mouse.getY() <= parent.position.y + parent.size.y;

    }

    public boolean buttonPressed() {

        return Mouse.isButtonDown(0) && buttonHovered();

    }

}
