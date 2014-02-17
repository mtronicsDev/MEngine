package mEngine.interactive.components;

import mEngine.core.GameController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.Collider;
import mEngine.physics.forces.Force;
import mEngine.physics.forces.ForceController;
import mEngine.util.TimeHelper;
import mEngine.util.vectorHelper.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class MovementComponent extends Component {

    public List<Force> forces = new ArrayList<Force>();
    public Vector3f speed;
    public Vector3f movedSpace;
    Vector3f previousSpeed;
    boolean sprinting;
    boolean sneaking;
    float mass = 0;

    public MovementComponent() {

        for(Force force : ForceController.forces) forces.add(force);

        speed = new Vector3f();
        movedSpace = new Vector3f();
        previousSpeed = new Vector3f();

    }

    public void onCreation(GameObject obj) {

        RenderComponent renderComponent = (RenderComponent)obj.getComponent("renderComponent");

        if(renderComponent != null) mass = renderComponent.model.getMass();

        else mass = 60;

    }

    public void onUpdate(GameObject obj) {

        if(!GameController.isGamePaused) {

            ControlComponent controlComponent = (ControlComponent)obj.getComponent("controlComponent");
            CollideComponent collideComponent = (CollideComponent)obj.getComponent("collideComponent");

            if(controlComponent != null) {

                if(!controlComponent.controller.sprintModeToggle) sprinting = false;
                if(!controlComponent.controller.sneakModeToggle) sneaking = false;

                controlComponent.onRemoteUpdate(obj);

            }

            for(int count = 8; count < forces.size(); count ++) {

                Force force = forces.get(count);

                //TODO: insert a method to calculate the sliding factor (friction) of the triangle the object is moving on to calculate the force direction subtraction

                force.direction = VectorHelper.divideVectors(force.direction, new Vector3f(2, 2, 2));

                if(Math.abs(force.direction.x) <= 0.001f &&
                        Math.abs(force.direction.y) <= 0.001f &&
                        Math.abs(force.direction.z) <= 0.001f) {

                    forces.remove(force);

                }

            }

            Vector3f forceSum = ForceController.sumForces(forces);

            if(collideComponent != null) {

                if(Collider.isCollidingWithSomething(obj)) {

                    forceSum = ForceController.getCombinedForces(forceSum.x, forceSum.y, forceSum.z);

                } else {

                    Vector2f newForces = ForceController.getCombinedForces(forceSum.x, forceSum.z);

                    forceSum.x = newForces.x;
                    forceSum.z = newForces.y;

                }

            }

            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            float deltaTime = TimeHelper.deltaTime;

            speed = ForceController.getSpeed(acceleration, speed, deltaTime);
            speed = VectorHelper.subtractVectors(speed, previousSpeed);
            previousSpeed = speed;

            movedSpace = ForceController.getMovedSpace(speed, deltaTime);

            if(collideComponent != null) collideComponent.onRemoteUpdate(obj);

            obj.position = VectorHelper.sumVectors(new Vector3f[] {obj.position, movedSpace});

        }

    }

    public void onRemoteUpdate(GameObject obj) {}

    public void moveForward(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(1);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(3, 0, 3)});

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

    public void moveBackward(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(2);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 0, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveLeft(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(3);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 0, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveRight(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(4);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

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

        if(sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0, 3, 0)});

            direction.y = newDirection.y;

        } else if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0, 0.3f, 0)});

            direction.y = newDirection.y;

        }

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

    public void jump(GameObject obj) { forces.get(7).enabled = true; }

    public void sprint(GameObject obj) {

        ControlComponent controlComponent = (ControlComponent)obj.getComponent("controlComponent");

        if(!controlComponent.controller.sprintModeToggle) {

            sprinting = true;
            sneaking = false;

        } else {

            sprinting = !sprinting;
            sneaking = false;

        }

    }

    public void sneak(GameObject obj) {

        ControlComponent controlComponent = (ControlComponent)obj.getComponent("controlComponent");

        if(!sprinting) {

            sneaking = !controlComponent.controller.sneakModeToggle || !sneaking;

        }

    }

    public void rotate(float pitch, float yaw, GameObject obj) {

        obj.rotation.x = pitch;
        obj.rotation.y = yaw;

    }

}
