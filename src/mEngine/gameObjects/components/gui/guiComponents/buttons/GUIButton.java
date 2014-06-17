package mEngine.gameObjects.components.gui.guiComponents.buttons;

import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIComponent;
import mEngine.gameObjects.components.gui.guiComponents.GUIQuad;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import static mEngine.util.input.Input.isButtonPressed;
import static mEngine.util.input.Input.isButtonUp;

public class GUIButton extends GUIComponent {

    //Later used for rising edge / falling edge detection
    boolean isButtonPressed;
    boolean isButtonHovered;
    boolean previouslyPressed;
    public ButtonPressingMethod buttonPressingMethod;

    public GUIButton(ButtonPressingMethod buttonPressingMethod) {

        this.buttonPressingMethod = buttonPressingMethod;

    }

    public void onUpdate() {

        super.onUpdate();

        previouslyPressed = isButtonPressed;

        isButtonHovered = buttonHovered();
        isButtonPressed = buttonPressed();

        if (isButtonActivated()) buttonPressingMethod.onPressing();

    }

    public boolean buttonHovered() {

        return Mouse.getX() >= parent.getPosition().x && Mouse.getX() <= parent.getPosition().x + parent.getSize().x &&
                Display.getHeight() - Mouse.getY() >= parent.getPosition().y && Display.getHeight() - Mouse.getY() <= parent.getPosition().y + parent.getSize().y;

    }

    public boolean buttonPressed() {

        return isButtonPressed(0) && isButtonHovered;

    }

    public boolean buttonReleased() {

        return isButtonUp(0) && isButtonHovered;

    }

    public boolean isButtonActivated() {

        return buttonReleased() && previouslyPressed;

    }

}
