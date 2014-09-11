package mEngine.gameObjects.modules.interaction;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.controls.Controller;
import mEngine.gameObjects.modules.gui.GUIElement;
import mEngine.gameObjects.modules.gui.modules.GUIText;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.threading.ThreadHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class InteractionModule extends Module {

    public boolean enabled;
    public Integer interactionKeyIndex;
    public String interactionKey;
    public String interactionDescription;
    public String interactionInstruction;
    public Interaction interaction;
    private float radius;
    private float[] controllerDistances;
    private float maxControllerLookAngle;
    private float[] controllerLookAngles;
    private List<GameObject> controlledGameObjects;
    public GUIText interactionInstructionText;

    public InteractionModule(boolean enabled, float radius, Interaction interaction) {

        this(enabled, radius, null, "interact", 180, interaction);

    }

    public InteractionModule(boolean enabled, float radius, String interactionKey, Interaction interaction) {

        this(enabled, radius, interactionKey, "interact", 180, interaction);


    }

    public InteractionModule(boolean enabled, float radius, String interactionKey, String interactionDescription, Interaction interaction) {

        this(enabled, radius, interactionKey, interactionDescription, 180, interaction);

    }

    public InteractionModule(boolean enabled, float radius, String interactionKey, String interactionDescription, float maxControllerLookAngle, Interaction interaction) {

        this.enabled = enabled;
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

            interactionInstructionText = new GUIText(interactionInstruction, 15);

            parent.addModule(
                    new GUIElement(new Vector2f(Display.getWidth() / 2, Display.getHeight() - 100), new Vector2f()).addComponent(interactionInstructionText)
            );

            this.interactionInstruction = interactionInstruction;

        }

    }

    public boolean isInteracted() {

        if (enabled) {

            boolean interacted = false;

            for (int count = 0; count < controlledGameObjects.size(); count++) {

                if (interactionKey == null) {

                    if (controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                        interacted = true;
                        break;

                    }

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

            if (object.getModule(Controller.class) != null) controlledGameObjects.add(object);

        }

        controllerDistances = new float[controlledGameObjects.size()];
        controllerLookAngles = new float[controlledGameObjects.size()];

    }

    public void onUpdate() {

        if (controlledGameObjects == null) setControlledGameObjects();

        setControllerData();

        if (interactionInstruction != null) {

            boolean enabled = false;

            for (int count = 0; count < controlledGameObjects.size(); count++) {

                if (controllerDistances[count] <= radius && controllerLookAngles[count] <= maxControllerLookAngle) {

                    enabled = true;
                    break;

                }

            }

            if (enabled && this.enabled)
                interactionInstructionText.text = interactionInstruction;

            else
                interactionInstructionText.text = "";

        }

        if (isInteracted()) {

            if (interaction instanceof StandardInteraction) ((StandardInteraction) interaction).interact();

            else ThreadHelper.startThread(((AsyncInteraction) interaction));

        }

    }

}
