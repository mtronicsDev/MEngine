package mEngine.gameObjects.components.gui.guiComponents;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import static mEngine.util.input.Input.isButtonPressed;
import static mEngine.util.input.Input.isButtonUp;

public class GUIButton extends GUIComponent {

    //Later used for rising edge / falling edge detection
    boolean isButtonPressed;
    boolean isButtonHovered;
    boolean pressedBeforeRelease;

    public GUIButton() {
    }

    public void onUpdate() {

        super.onUpdate();

        isButtonPressed = buttonPressed();
        isButtonHovered = buttonHovered();

        if (!pressedBeforeRelease) pressedBeforeRelease = isButtonPressed;

    }

    public boolean buttonHovered() {

        return Mouse.getX() >= parent.position.x && Mouse.getX() <= parent.position.x + parent.size.x &&
                Display.getHeight() - Mouse.getY() >= parent.position.y && Display.getHeight() - Mouse.getY() <= parent.position.y + parent.size.y;

    }

    public boolean buttonPressed() {

        return isButtonPressed(0) && buttonHovered();

    }

    public boolean buttonReleased() {

        return isButtonUp(0) && buttonHovered();

    }

    public boolean isButtonActivated() {

        boolean buttonActivated = buttonReleased() && pressedBeforeRelease;

        if (buttonActivated) pressedBeforeRelease = false;

        return buttonActivated;

    }

}
