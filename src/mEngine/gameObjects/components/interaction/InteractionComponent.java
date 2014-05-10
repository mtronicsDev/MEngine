package mEngine.gameObjects.components.interaction;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.controls.Controller;
import mEngine.gameObjects.components.gui.GUIElement;
import mEngine.gameObjects.components.gui.guiComponents.GUIText;
import mEngine.gameObjects.components.interaction.methods.AsyncMethod;
import mEngine.gameObjects.components.interaction.methods.InteractionMethod;
import mEngine.gameObjects.components.interaction.methods.NormalMethod;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.threading.ThreadHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

public class InteractionComponent extends Component {

    public boolean interactable;
    public Integer interactionKeyIndex;
    public String interactionKey;
    public String interactionDescription;
    public String interactionInstruction;
    public float radius;
    public float controllerDistance;

    public InteractionMethod interaction;

    public InteractionComponent(boolean interactable, float radius, InteractionMethod interaction) {

        this.interactable = interactable;
        this.interaction = interaction;
        this.radius = radius;

    }

    public InteractionComponent(boolean interactable, float radius, String interactionKey, InteractionMethod interaction) {

        this.interactable = interactable;
        this.interactionKey = interactionKey;
        this.interactionKeyIndex = Keyboard.getKeyIndex(interactionKey);
        this.interaction = interaction;
        this.radius = radius;

    }

    public InteractionComponent(boolean interactable, float radius, String interactionKey, String interactionDescription, InteractionMethod interaction) {

        this(interactable, radius, interactionKey, interaction);

        this.interactionDescription = interactionDescription;

    }

    public void onCreation(GameObject object) {

        super.onCreation(object);

        interaction.setParent(parent);

        if (interactionKey != null) {

            String interactionInstruction = String.valueOf(interactionKey).toUpperCase() + ": ";

            if (interactionDescription == null) interactionInstruction += "interact";

            else interactionInstruction += interactionDescription;

            parent.addComponent(
                    "interactionInstruction",
                    new GUIElement(new Vector2f(Display.getWidth() / 2, Display.getHeight() - 100), new Vector2f()).addComponent("text", new GUIText(interactionInstruction, 15))
            );

            this.interactionInstruction = interactionInstruction;

        }

    }

    public boolean isInteracted() {

        if (interactable) {

            boolean interacted = false;

            if (interactionKey == null) {

                if (controllerDistance <= radius) interacted = true;

            } else {

                if (Input.isKeyDown(interactionKeyIndex) && controllerDistance <= radius) interacted = true;

            }

            return interacted;

        } else return false;

    }

    private float getControllerDistance() {

        GameObject controlledObject = null;

        for (GameObject object : ObjectController.gameObjects) {

            for (Component component : object.components.values()) {

                if (component instanceof Controller) {

                    controlledObject = object;
                    break;

                }

            }

        }

        float distance;

        if (controlledObject == null) distance = Float.POSITIVE_INFINITY;

        else distance = VectorHelper.getAbs(VectorHelper.subtractVectors(controlledObject.position, parent.position));

        return distance;

    }

    public void onUpdate() {

        controllerDistance = getControllerDistance();

        if (interactionInstruction != null) {

            if (controllerDistance <= radius && interactable)
            ((GUIText) ((GUIElement) parent.getComponent("interactionInstruction")).getComponent("text")).text = interactionInstruction;

            else ((GUIText) ((GUIElement) parent.getComponent("interactionInstruction")).getComponent("text")).text = "";

        }

        if (isInteracted()) {

            if (interaction instanceof NormalMethod) ((NormalMethod) interaction).interact();

            else ThreadHelper.startThread(((AsyncMethod) interaction));

        }

    }

}
