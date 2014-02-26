package mEngine.interactive.gameObjects.components;

import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.forces.Force;
import org.lwjgl.util.vector.Vector3f;

public abstract class Controller extends Component{

    public float[] forceStrengths;
    public boolean capableOfFlying;
    public boolean sneakModeToggle;
    public boolean sprintModeToggle;
    public boolean continuouslyJumping;
    public float rotationSpeed;

    public Controller(float[] forceStrengths, boolean capableOfFlying) {

        this.forceStrengths = forceStrengths;
        this.capableOfFlying = capableOfFlying;

    }

    public void onCreation(GameObject obj) {

        MovementComponent movementComponent = (MovementComponent)obj.getComponent("movementComponent");

        if(movementComponent != null) {

            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(0, 0, -forceStrengths[0]))); //Forward
            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(0, 0, forceStrengths[1]))); //Backward
            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(forceStrengths[2], 0, 0))); //Right
            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(-forceStrengths[3], 0, 0))); //Left
            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(0, forceStrengths[4], 0))); //Up
            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(0, -forceStrengths[5], 0))); //Down
            movementComponent.forcePoints.get("middle").forces.add(new Force(new Vector3f(0, forceStrengths[6], 0))); //Jump force

        }


    }

    public void onRemoteUpdate(GameObject obj) { updateObject(obj); }

    protected abstract void updateObject(GameObject obj);

}
