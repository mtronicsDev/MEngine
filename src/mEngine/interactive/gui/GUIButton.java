package mEngine.interactive.gui;

import mEngine.interactive.gui.primitives.GUIQuad;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class GUIButton extends GUIQuad {

    //Later used for rising edge / falling edge detection
    boolean isButtonPressed;
    boolean isButtonHovered;

    public GUIButton(Vector2f pos, Vector2f size) { this(pos, new Vector2f(), size); }

    public GUIButton(Vector2f pos, Vector2f rot, Vector2f size) {

        super(pos, rot, size);

    }

    public void update() {

        super.update();
        isButtonPressed = buttonPressed();
        isButtonHovered = buttonHovered();

    }

    public boolean buttonHovered() {

        return Mouse.getX() >= position.x && Mouse.getX() <= position.x + size.x &&
                Display.getHeight() - Mouse.getY() >= position.y && Display.getHeight() - Mouse.getY() <= position.y + size.y;

    }

    public boolean buttonPressed() {

        return Mouse.isButtonDown(0) && buttonHovered();

    }

}
