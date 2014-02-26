package mEngine.interactive.gui.guiComponents;

import mEngine.interactive.gui.GUIElement;
import mEngine.util.TextureHelper;
import org.lwjgl.util.vector.Vector2f;

import static mEngine.util.MathHelper.clamp;
import static org.lwjgl.opengl.GL11.*;

public class GUIGraph extends GUIComponent {

    protected Vector2f size;
    protected double[] values;

    public GUIGraph(Vector2f size, double[] values) {

        this.size = size;
        this.values = values;
        if(values.length == 0) {

            this.values = new double[(int)size.x];
            for(int i = 0; i < size.x; i++) { this.values[i] = 0; }

        }

    }

    public void onUpdate(GUIElement element) {

        super.onUpdate(element);
        float stepSize = size.x / values.length;

        TextureHelper.getTexture("texturedStar").bind(); //Temporary fix
        glBegin(GL_LINE_STRIP);

        //For every x-value, a vertex is rendered at the appropriate spot
        for(int i = 0; i < values.length; i++) {

            /*
            StepSize: size of the jumps between x values
            y values: The point is moved to the y-position of the element.
                Then it is moved down by size.y so it is in the bottom left corner of the element.
                Finally, it is moved up again by the y value given.
            */
            glVertex2f(element.position.x + stepSize * i, element.position.y + size.y - (float)clamp(values[i], 0, size.y));

        }

        glEnd();

    }

    public void onExternalUpdate(Object[] args) {

        super.onExternalUpdate(args);
        for(int i = 0; i < args.length; i++) { values[i] = (Double)args[i]; }

    }

}
