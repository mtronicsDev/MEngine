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
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class InteractionComponent extends Component {

    public boolean interactable;
    public Integer interactionKeyIndex;
    public String interactionKey;
    public String interactionDescription;
    public String interactionInstruction;
    private float radius;
    private float[] controllerDistances;
    private float maxControllerLookAngle;
    private float[] controllerLookAngles;
    private List<GameObject> controlledGameObjects;

    public InteractionMethod interaction;

    public InteractionComponent(boolean interactable, float radius, InteractionMethod interaction) {

        this(interactable, radius, null, "interact", 180, interaction);

    }

    public InteractionComponent(boolean interactable, float radius, String interactionKey, InteractionMethod interaction) {

        this(interactable, radius, interactionKey, "interact",180, interaction);


    }

    public InteractionComponent(boolean interactable, float radius, String interactionKey, String interactionDescription, InteractionMethod interaction) {

        this(interactable, radius, interactionKey, interactionDescription,180, interaction);

    }

    public InteractionComponent(boolean interactable, float radius, String interactionKey, String interactionDescription, float maxControllerLookAngle, InteractionMethod interaction) {

        this.interactable = interactable;
        this.interactionKey = interactionKey;
        this.interactionKeyIndex = Keyboard.getKeyIndex(interactionKey);
        this.interaction = interaction;
        this.radius = radius;
        this.interactionDescription = interactionDescription;
        this.maxControllerLookAngle = (float) Math.toRadians(maxControllerLookAngle);

    }

    public void onCreation(GameObject object) {

        super.onCreation(object);

        interaction.setParent(parent);

        if (interactionKey != null) {

            String interactionInstruction = String.valueOf(interactionKey).toUpperCase() + ": " + interactionDescription;

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

            for (int count = 0; count < controlledGameObjects.size(); count++) {

                if (interactionKey == null) {

                    if (controllerDistances[count] <= radius) interacted = true;

                } else {

                    if (Input.isKeyDown(interactionKeyIndex) && controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                        interacted = true;
                        break;

                    }

                }

            }

            return interacted;

        } else return false;

    }

    private void setControllerData() {

        for (int count = 0; count < controlledGameObjects.size(); count++) {

            GameObject controlledObject = controlledGameObjects.get(count);

            Vector3f distanceVector = VectorHelper.subtractVectors(parent.position, controlledObject.position);

            controllerDistances[count] = VectorHelper.getAbs(distanceVector);

            Vector3f percentRotation = new Vector3f(controlledObject.percentRotation.x, controlledObject.percentRotation.y, -controlledObject.percentRotation.z);
            controllerLookAngles[count] = VectorHelper.getAngle(distanceVector, percentRotation);

        }

    }

    private void setControlledGameObjects() {

        controlledGameObjects = new ArrayList<GameObject>();

        for (GameObject object : ObjectController.gameObjects) {

            for (Component component : object.components.values()) {

                if (component instanceof Controller) controlledGameObjects.add(object);

            }

        }

        controllerDistances = new float[controlledGameObjects.size()];
        controllerLookAngles = new float[controlledGameObjects.size()];

    }

    public void onUpdate() {

        if (controlledGameObjects == null) setControlledGameObjects();

        setControllerData();

        if (interactionInstruction != null) {

            boolean interactable = false;

            for (int count = 0; count < controlledGameObjects.size(); count++) {

                if (controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                    interactable = true;
                    break;

                }

            }

            if (interactable && this.interactable) ((GUIText) ((GUIElement) parent.getComponent("interactionInstruction")).getComponent("text")).text = interactionInstruction;

            else ((GUIText) ((GUIElement) parent.getComponent("interactionInstruction")).getComponent("text")).text = "";

        }

        if (isInteracted()) {

            if (interaction instanceof NormalMethod) ((NormalMethod) interaction).interact();

            else ThreadHelper.startThread(((AsyncMethod) interaction));

        }

    }

}
