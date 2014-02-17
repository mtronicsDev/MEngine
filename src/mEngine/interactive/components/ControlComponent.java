package mEngine.interactive.components;

import mEngine.interactive.controls.Controller;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.forces.Force;
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

    public void onCreation(GameObject obj) {

        MovementComponent movementComponent = (MovementComponent)obj.getComponent("movementComponent");

        if(movementComponent != null) {

            movementComponent.forces.add(new Force(new Vector3f(0, 0, -forceStrengths[0] / 10))); //Forward
            movementComponent.forces.add(new Force(new Vector3f(0, 0, forceStrengths[1] / 10))); //Backward
            movementComponent.forces.add(new Force(new Vector3f(forceStrengths[2] / 10, 0, 0))); //Right
            movementComponent.forces.add(new Force(new Vector3f(-forceStrengths[3] / 10, 0, 0))); //Left
            movementComponent.forces.add(new Force(new Vector3f(0, forceStrengths[4] / 10, 0))); //Up
            movementComponent.forces.add(new Force(new Vector3f(0, -forceStrengths[5] / 10, 0))); //Down
            movementComponent.forces.add(new Force(new Vector3f(0, forceStrengths[6] / 10, 0))); //Jump force

        }


    }

    public void onUpdate(GameObject obj) {}
    public void onRemoteUpdate(GameObject obj) { controller.updateObject(obj); }

}
