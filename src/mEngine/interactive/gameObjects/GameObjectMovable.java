package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.graphics.renderable.Model;
import mEngine.interactive.controls.Controller;
import mEngine.physics.Collider;
import mEngine.physics.Force;
import mEngine.physics.ForceController;
import mEngine.util.TimeHelper;
import mEngine.util.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectMovable extends GameObject{

    protected Controller controller;
    public float mass = -1;
    public Vector3f speed = new Vector3f();
    public Model model = null;

    public boolean sprinting;
    public boolean sneaking;
    public boolean capableOfFlying;

    public GameObjectMovable(Vector3f pos, Vector3f rot, float[] forceStrengths, Controller controller, boolean capableOfFlying) {

        super(pos, rot, forceStrengths);
        this.controller = controller;

        if(mass == -1) mass = 60;

    }

    public GameObjectMovable(Vector3f pos, Vector3f rot, Controller controller, boolean capableOfFlying) {

        super(pos, rot);
        this.controller = controller;

        if(mass == -1) mass = 60;

    }

    public void update() {

        updateModel();

        if(!GameController.isGamePaused) {

            //forces.get(0).enabled = true;
            if(controller != null) if(!controller.sprintModeToggle) sprinting = false;
            if(controller != null) if(!controller.sneakModeToggle) sneaking = false;

            updateController();

            for(int count = 8; count < forces.size(); count ++) {

                Force force = forces.get(count);

                //TODO: insert a method to calculate the sliding factor of the triangle the object is moving on to calculate the force direction subtraction

                force.direction = VectorHelper.multiplyVectors(new Vector3f[] { force.direction, new Vector3f(0.2f, 0.2f, 0.2f) });

                if(Math.abs(force.direction.x) <= 0.1f &&
                        Math.abs(force.direction.y) <= 0.1f &&
                        Math.abs(force.direction.z) <= 0.1f) {

                    forces.remove(force);

                }

            }

            Vector3f forceSum = ForceController.sumForces(forces);

            if(Collider.isCollidingWithSomething(this)) {

                if(forceSum.x != 0 && forceSum.z != 0) {

                    forceSum = ForceController.getCombinedForces(forceSum.x, forceSum.y, forceSum.z);

                }

            } else {

                if(forceSum.x != 0 && forceSum.z != 0) {

                    Vector2f newForces = ForceController.getCombinedForces(forceSum.x, forceSum.z);

                    forceSum.x = newForces.x;
                    forceSum.z = newForces.y;

                }

            }

            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime());
            speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime());

            if(model != null && (movedSpace.x != 0 || movedSpace.y != 0 || movedSpace.z != 0)) movedSpace = Collider.getMovedSpace(movedSpace, this);

            position = VectorHelper.sumVectors(new Vector3f[] {position, movedSpace});

        }

    }

    protected void updateController() {}
    protected void updateModel() {}

    public void moveForward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(1);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        if(sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(3, 0, 3)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        } else if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 0, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveBackward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(2);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 0, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveLeft() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(3);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 0, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveRight() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(4);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 0, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveUp() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(5);

        direction.y = givenForce.direction.y;

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveDown() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(6);

        direction.y = givenForce.direction.y;

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void jump() { forces.get(7).enabled = true; }

    public void sprint() {

        if(controller != null) {

            if(!controller.sprintModeToggle) {

                sprinting = true;
                sneaking = false;

            } else {

                sprinting = !sprinting;
                sneaking = false;

            }

        }

    }

    public void sneak() {

        if(!sprinting) {

            if(controller != null) sneaking = !controller.sneakModeToggle || !sneaking;

        }

    }

    public void rotate(float pitch, float yaw) {

        rotation.x = pitch;
        rotation.y = yaw;

    }

}
