package mEngine.gameObjects.components;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.controls.Controller;
import mEngine.gameObjects.components.rendering.RenderComponent;
import mEngine.physics.collisions.Collider;
import mEngine.physics.forces.Force;
import mEngine.physics.forces.ForceController;
import mEngine.physics.forces.ForcePoint;
import mEngine.util.TimeHelper;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.Matrix3d;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;

public class MovementComponent extends Component {

    public HashMap<String, ForcePoint> forcePoints = new HashMap<String, ForcePoint>();
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

        super.onCreation(obj);
        RenderComponent renderComponent = (RenderComponent) parent.getComponent("renderComponent");

        if (renderComponent != null) {

            mass = renderComponent.model.mass;

            forcePoints.put("middle", new ForcePoint(new Vector3f()));
            forcePoints.put("forward", new ForcePoint(new Vector3f(0, 0, -1)));
            forcePoints.put("backward", new ForcePoint(new Vector3f(0, 0, 1)));
            forcePoints.put("left", new ForcePoint(new Vector3f(-1, 0, 0)));
            forcePoints.put("right", new ForcePoint(new Vector3f(1, 0, 0)));
            forcePoints.put("up", new ForcePoint(new Vector3f(0, 1, 0)));
            forcePoints.put("down", new ForcePoint(new Vector3f(0, -1, 0)));

        } else {

            mass = 60;

            forcePoints.put("middle", new ForcePoint(new Vector3f()));

        }

        for (String key : ForceController.generalForces.keySet()) {

            forcePoints.get("middle").forces.put(key, ForceController.generalForces.get(key));

        }

    }

    public void onUpdate() {

        if (!GameController.isGamePaused) {

            Controller controller = (Controller) parent.getComponent("controller");
            CollideComponent collideComponent = (CollideComponent) parent.getComponent("collideComponent");

            if (controller != null) {

                if (!controller.sprintModeToggle) sprinting = false;
                if (!controller.sneakModeToggle) sneaking = false;
                if (Input.isKeyDown(Keyboard.KEY_V))
                    forcePoints.get("middle").forces.get("gravity").enabled = !forcePoints.get("middle").forces.get("gravity").enabled; //Kiwi (KEY_V) :P

                controller.onRemoteUpdate();

            }

            parent.percentRotation = new Vector3f(0, 0, 1);

            Matrix3d xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                    new Vector3f(0, (float) Math.cos(Math.toRadians(parent.rotation.x)), (float) -Math.sin(Math.toRadians(parent.rotation.x))),
                    new Vector3f(0, (float) Math.sin(Math.toRadians(parent.rotation.x)), (float) Math.cos(Math.toRadians(parent.rotation.x))));
            parent.percentRotation = xAxisRotationMatrix.multiplyByVector(parent.percentRotation);

            Matrix3d yAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(Math.toRadians(parent.rotation.y)), 0, (float) Math.sin(Math.toRadians(parent.rotation.y))),
                    new Vector3f(0, 1, 0),
                    new Vector3f((float) -Math.sin(Math.toRadians(parent.rotation.y)), 0, (float) Math.cos(Math.toRadians(parent.rotation.y))));
            parent.percentRotation = yAxisRotationMatrix.multiplyByVector(parent.percentRotation);

            if (Input.isKeyPressed(Keyboard.KEY_E)) System.out.println(parent.percentRotation);

            for (ForcePoint forcePoint : forcePoints.values()) {

                for (String key : forcePoint.forces.keySet()) {

                    if (key.startsWith("inertiaForce")) {

                        Force force = forcePoint.forces.get(key);

                        //TODO: insert a method to calculate the sliding factor (friction) of the triangle the object is moving on to calculate the force direction subtraction

                        force.direction = VectorHelper.divideVectorByFloat(force.direction, 2);

                        if (Math.abs(force.direction.x) <= 0.001f &&
                                Math.abs(force.direction.y) <= 0.001f &&
                                Math.abs(force.direction.z) <= 0.001f) {

                            forcePoint.forces.remove(force);

                        }

                    }

                }

            }

            Vector3f forceSum = ForceController.sumForces(forcePoints.get("middle").forces.values());

            if (collideComponent != null) {

                if (Collider.isCollidingWithSomething(parent)) {

                    forceSum = ForceController.getCombinedForces(forceSum);

                } else {

                    Vector2f newForces = ForceController.getCombinedForces(new Vector2f(forceSum.x, forceSum.z));

                    forceSum.x = newForces.x;
                    forceSum.z = newForces.y;

                }

            } else forceSum = ForceController.getCombinedForces(forceSum);

            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            float deltaTime = TimeHelper.deltaTime;

            speed = ForceController.getSpeed(acceleration, speed, deltaTime);
            speed = VectorHelper.subtractVectors(speed, previousSpeed);
            previousSpeed = speed;

            movedSpace = ForceController.getMovedSpace(speed, deltaTime);

            if (collideComponent != null && collideComponent.ableToCollide && !VectorHelper.areEqual(movedSpace, new Vector3f()))
                collideComponent.onRemoteUpdate();

            parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, movedSpace});

        }

    }

    public void onRemoteUpdate() {
    }

    //TODO: Find a way to hold the force counter as little as possible because otherwise there could be an overflow error
    public void moveForward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get("forward");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(3, 1, 3)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        } else if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forcePoints.get("middle").forceCount;

        forcePoints.get("middle").forces.put(forceIdentifier, new Force(direction));
        forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;

        forcePoints.get("middle").forceCount++;

    }

    public void moveBackward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get("backward");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forcePoints.get("middle").forceCount;

        forcePoints.get("middle").forces.put(forceIdentifier, new Force(direction));
        forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;

        forcePoints.get("middle").forceCount++;

    }

    public void moveLeft() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get("left");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forcePoints.get("middle").forceCount;

        forcePoints.get("middle").forces.put(forceIdentifier, new Force(direction));
        forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;

        forcePoints.get("middle").forceCount++;

    }

    public void moveRight() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get("right");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forcePoints.get("middle").forceCount;

        forcePoints.get("middle").forces.put(forceIdentifier, new Force(direction));
        forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;

        forcePoints.get("middle").forceCount++;

    }

    public void moveUp() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get("up");

        direction.y = givenForce.direction.y;

        if (sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(1, 3, 1)});

            direction.y = newDirection.y;

        } else if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(1, 0.3f, 1)});

            direction.y = newDirection.y;

        }

        String forceIdentifier = "inertiaForce" + forcePoints.get("middle").forceCount;

        forcePoints.get("middle").forces.put(forceIdentifier, new Force(direction));
        forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;

        forcePoints.get("middle").forceCount++;

    }

    public void moveDown() {

        Vector3f direction = new Vector3f();
        Force givenForce = forcePoints.get("middle").forces.get("down");

        direction.y = givenForce.direction.y;

        String forceIdentifier = "inertiaForce" + forcePoints.get("middle").forceCount;

        forcePoints.get("middle").forces.put(forceIdentifier, new Force(direction));
        forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;

        forcePoints.get("middle").forceCount++;

    }

    public void jump() {

        forcePoints.get("middle").forces.get("jump").enabled = true;

    }

    public void sprint() {

        Controller controller = (Controller) parent.getComponent("controller");

        if (!controller.sprintModeToggle) {

            sprinting = true;
            sneaking = false;

        } else {

            sprinting = !sprinting;
            sneaking = false;

        }

    }

    public void sneak() {

        Controller controller = (Controller) parent.getComponent("controller");

        if (!sprinting) {

            sneaking = !controller.sneakModeToggle || !sneaking;

        }

    }

    public void rotate(float pitch, float yaw) {

        parent.rotation.x = pitch;
        parent.rotation.y = yaw;

    }

}
