package mEngine.interactive.components;

import mEngine.core.GameController;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.physics.Collider;
import mEngine.physics.forces.Force;
import mEngine.physics.forces.ForceController;
import mEngine.physics.forces.ForcePoint;
import mEngine.util.TimeHelper;
import mEngine.util.vectorHelper.VectorHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovementComponent extends Component {

    public List<Force> forces = new ArrayList<Force>();
    public Map<String, ForcePoint> forcePoints = new HashMap<String, ForcePoint>();
    public Vector3f speed;
    public Vector3f movedSpace;
    Vector3f previousSpeed;
    boolean sprinting;
    boolean sneaking;
    float mass = 0;

    public MovementComponent() {

        speed = new Vector3f();
        movedSpace = new Vector3f();
        previousSpeed = new Vector3f();

    }

    public void onCreation(GameObject obj) {

        RenderComponent renderComponent = (RenderComponent)obj.getComponent("renderComponent");

        if(renderComponent != null) {

            mass = renderComponent.model.getMass();

            Vector3f modelMiddle = VectorHelper.divideVectors(renderComponent.model.getSize(), new Vector3f(2, 2, 2));
            Vector3f maxModelVertexPos = VectorHelper.subtractVectors(renderComponent.model.getMaxVertexPos(), modelMiddle);
            Vector3f minModelVertexPos = VectorHelper.subtractVectors(renderComponent.model.getMinVertexPos(), modelMiddle);

            forcePoints.put("middle", new ForcePoint(modelMiddle));
            forcePoints.put("forward", new ForcePoint(new Vector3f(0, 0, minModelVertexPos.z)));
            forcePoints.put("backward", new ForcePoint(new Vector3f(0, 0, maxModelVertexPos.z)));
            forcePoints.put("left", new ForcePoint(new Vector3f(minModelVertexPos.x, 0, 0)));
            forcePoints.put("right", new ForcePoint(new Vector3f(maxModelVertexPos.x, 0, 0)));
            forcePoints.put("up", new ForcePoint(new Vector3f(0, maxModelVertexPos.y, 0)));
            forcePoints.put("down", new ForcePoint(new Vector3f(0, minModelVertexPos.y, 0)));

        }

        else {

            mass = 60;

            forcePoints.put("middle", new ForcePoint(new Vector3f()));

        }

        for(Force force : ForceController.forces) forcePoints.get("middle").forces.add(force);

    }

    public void onUpdate(GameObject obj) {

        if(!GameController.isGamePaused) {

            Controller controller = (Controller)obj.getComponent("controller");
            CollideComponent collideComponent = (CollideComponent)obj.getComponent("collideComponent");

            if(controller != null) {

                if(!controller.sprintModeToggle) sprinting = false;
                if(!controller.sneakModeToggle) sneaking = false;

                controller.onRemoteUpdate(obj);

            }

            for(ForcePoint forcePoint : forcePoints.values()) {

                for(int count = 8; count < forcePoint.forces.size(); count ++) {

                    Force force = forcePoint.forces.get(count);

                    //TODO: insert a method to calculate the sliding factor (friction) of the triangle the object is moving on to calculate the force direction subtraction

                    force.direction = VectorHelper.divideVectors(force.direction, new Vector3f(2, 2, 2));

                    if(Math.abs(force.direction.x) <= 0.001f &&
                            Math.abs(force.direction.y) <= 0.001f &&
                            Math.abs(force.direction.z) <= 0.001f) {

                        forcePoint.forces.remove(force);

                    }

                }

            }

            Vector3f forceSum = ForceController.sumForces(forcePoints.get("middle").forces);

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

            if(collideComponent != null && !VectorHelper.areEqual(movedSpace, new Vector3f())) collideComponent.onRemoteUpdate(obj);

            obj.position = VectorHelper.sumVectors(new Vector3f[] {obj.position, movedSpace});

        }

    }

    public void onRemoteUpdate(GameObject obj) {}

    public void moveForward(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get(1);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(3, 1, 3)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        } else if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forcePoints.get("middle").forces.add(new Force(direction));
        forcePoints.get("middle").forces.get(forcePoints.get("middle").forces.size() - 1).enabled = true;

    }

    public void moveBackward(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get(2);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forcePoints.get("middle").forces.add(new Force(direction));
        forcePoints.get("middle").forces.get(forcePoints.get("middle").forces.size() - 1).enabled = true;

    }

    public void moveLeft(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get(3);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forcePoints.get("middle").forces.add(new Force(direction));
        forcePoints.get("middle").forces.get(forcePoints.get("middle").forces.size() - 1).enabled = true;

    }

    public void moveRight(GameObject obj) {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get(4);

        direction.x = -(givenForce.direction.x * (float)Math.sin(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(obj.rotation.y)));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(obj.rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(obj.rotation.y));

        if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        forcePoints.get("middle").forces.add(new Force(direction));
        forcePoints.get("middle").forces.get(forcePoints.get("middle").forces.size() - 1).enabled = true;

    }

    public void moveUp() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get(5);

        direction.y = givenForce.direction.y;

        if(sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(1, 3, 1)});

            direction.y = newDirection.y;

        } else if(sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[] {direction, new Vector3f(1, 0.3f, 1)});

            direction.y = newDirection.y;

        }

        forcePoints.get("middle").forces.add(new Force(direction));
        forcePoints.get("middle").forces.get(forcePoints.get("middle").forces.size() - 1).enabled = true;

    }

    public void moveDown() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get(6);

        direction.y = givenForce.direction.y;

        forcePoints.get("middle").forces.add(new Force(direction));
        forcePoints.get("middle").forces.get(forcePoints.get("middle").forces.size() - 1).enabled = true;

    }

    public void jump() { forcePoints.get("middle").forces.get(7).enabled = true; }

    public void sprint(GameObject obj) {

        Controller controller = (Controller)obj.getComponent("controller");

        if(!controller.sprintModeToggle) {

            sprinting = true;
            sneaking = false;

        } else {

            sprinting = !sprinting;
            sneaking = false;

        }

    }

    public void sneak(GameObject obj) {

        Controller controller = (Controller)obj.getComponent("controller");

        if(!sprinting) {

            sneaking = !controller.sneakModeToggle || !sneaking;

        }

    }

    public void rotate(float pitch, float yaw, GameObject obj) {

        obj.rotation.x = pitch;
        obj.rotation.y = yaw;

    }

}
