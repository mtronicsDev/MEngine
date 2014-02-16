package mEngine.interactive.components;

import mEngine.core.ObjectController;
import mEngine.interactive.controls.Controller;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.forces.Force;
import mEngine.util.componentHelper.ComponentHelper;
import org.lwjgl.util.vector.Vector3f;

public class ControlComponent extends Component {

    public float[] forceStrengths;
    public Controller controller;
    public boolean capableOfFlying;
    public boolean sneakModeToggle;
    public boolean sprintModeToggle;

    public ControlComponent(Controller controller, boolean capableOfFlying, float[] forceStrengths) {

        this.controller = controller;
        this.capableOfFlying = capableOfFlying;

        this.forceStrengths = forceStrengths;

    }

    public void initialize(GameObject obj) {

        MovementComponent movementComponent = ComponentHelper.components.get(ObjectController.gameObjects.indexOf(obj)).movementComponent;

        if(movementComponent != null) {

            movementComponent.forces.add(new Force(new Vector3f(0, 0, -forceStrengths[0]))); //Forward
            movementComponent.forces.add(new Force(new Vector3f(0, 0, forceStrengths[1]))); //Backward
            movementComponent.forces.add(new Force(new Vector3f(forceStrengths[2], 0, 0))); //Right
            movementComponent.forces.add(new Force(new Vector3f(-forceStrengths[3], 0, 0))); //Left
            movementComponent.forces.add(new Force(new Vector3f(0, forceStrengths[4], 0))); //Up
            movementComponent.forces.add(new Force(new Vector3f(0, -forceStrengths[5], 0))); //Down
            movementComponent.forces.add(new Force(new Vector3f(0, forceStrengths[6], 0))); //Jump force

        }


    }

    public void update(GameObject obj) {

        controller.updateObject(obj);

    }

    public void updateByComponent(GameObject obj) {}

}
