package mEngine.gameObjects.components.controls;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.MovementComponent;
import mEngine.physics.forces.Force;
import org.lwjgl.util.vector.Vector3f;

public abstract class Controller extends Component {

    public float[] forceStrengths;
    public boolean capableOfFlying;
    public boolean sneakModeToggle;
    public boolean sprintModeToggle;
    public boolean continuouslyJumping;
    public float rotationSpeed;

    public Controller(float[] forceStrengths, boolean capableOfFlying) {

        this(forceStrengths, capableOfFlying, false);

    }

    public Controller(float[] forceStrengths, boolean capableOfFlying, boolean addedAsLast) {

        super(addedAsLast);

        this.forceStrengths = forceStrengths;

        for (int count = 0; count < this.forceStrengths.length; count++) this.forceStrengths[count] /= 2;

        this.capableOfFlying = capableOfFlying;

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        MovementComponent movementComponent = (MovementComponent) obj.getComponent("movementComponent");

        if (movementComponent != null) {

            movementComponent.forcePoints.get("middle").forces.put("forward", new Force(new Vector3f(0, 0, -forceStrengths[0])));
            movementComponent.forcePoints.get("middle").forces.put("backward", new Force(new Vector3f(0, 0, forceStrengths[1])));
            movementComponent.forcePoints.get("middle").forces.put("right", new Force(new Vector3f(-forceStrengths[2], 0, 0)));
            movementComponent.forcePoints.get("middle").forces.put("left", new Force(new Vector3f(forceStrengths[3], 0, 0)));
            movementComponent.forcePoints.get("middle").forces.put("up", new Force(new Vector3f(0, forceStrengths[4], 0)));
            movementComponent.forcePoints.get("middle").forces.put("down", new Force(new Vector3f(0, -forceStrengths[5], 0)));
            movementComponent.forcePoints.get("middle").forces.put("jump", new Force(new Vector3f(0, forceStrengths[6], 0)));

        }


    }

    public void onRemoteUpdate() {
        updateObject();
    }

    protected abstract void updateObject();

}
