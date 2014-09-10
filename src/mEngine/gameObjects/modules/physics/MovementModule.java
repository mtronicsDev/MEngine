package mEngine.gameObjects.modules.physics;

import mEngine.core.GameController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.controls.Controller;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.physics.forces.Force;
import mEngine.physics.forces.ForceController;
import mEngine.util.input.Input;
import mEngine.util.math.vectors.Matrix3f;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.time.TimeHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class MovementModule extends Module {

    public Map<String, Force> forces = new HashMap<String, Force>();
    public int forceCount = 0;
    public Vector3f speed;
    public Vector3f movedSpace;
    Vector3f previousSpeed;
    boolean sprinting;
    boolean sneaking;
    float mass = 0;

    public MovementModule() {

        speed = new Vector3f();
        movedSpace = new Vector3f();
        previousSpeed = new Vector3f();

    }

    public void onCreation(GameObject obj) {

        super.onCreation(obj);
        RenderModule renderComponent = (RenderModule) obj.getModule(RenderModule.class);

        if (renderComponent != null) mass = renderComponent.model.getMass();

        else  mass = 60;

        for (String key : ForceController.generalForces.keySet()) {

            forces.put(key, ForceController.generalForces.get(key));

        }

    }

    public void onUpdate() {

        if (!GameController.isGamePaused) {

            Controller controller = (Controller) parent.getModule(Controller.class);

            PhysicModule physicComponent = (PhysicModule) parent.getModule(PhysicModule.class);

            if (controller != null) {

                if (!controller.sprintModeToggle) sprinting = false;
                if (!controller.sneakModeToggle) sneaking = false;
                if (Input.isKeyDown(Keyboard.KEY_V))
                    forces.get("gravity").enabled = !forces.get("gravity").enabled; //Kiwi (KEY_V) :P

                controller.onRemoteUpdate();

            }

            parent.percentRotation = new Vector3f(0, 0, 1);

            Matrix3f xAxisRotationMatrix = new Matrix3f(new Vector3f(1, 0, 0),
                    new Vector3f(0, (float) Math.cos(Math.toRadians(parent.rotation.x)), (float) -Math.sin(Math.toRadians(parent.rotation.x))),
                    new Vector3f(0, (float) Math.sin(Math.toRadians(parent.rotation.x)), (float) Math.cos(Math.toRadians(parent.rotation.x))));
            parent.percentRotation = xAxisRotationMatrix.multiplyByVector(parent.percentRotation);

            Matrix3f yAxisRotationMatrix = new Matrix3f(new Vector3f((float) Math.cos(Math.toRadians(parent.rotation.y)), 0, (float) Math.sin(Math.toRadians(parent.rotation.y))),
                    new Vector3f(0, 1, 0),
                    new Vector3f((float) -Math.sin(Math.toRadians(parent.rotation.y)), 0, (float) Math.cos(Math.toRadians(parent.rotation.y))));
            parent.percentRotation = yAxisRotationMatrix.multiplyByVector(parent.percentRotation);

            for (String key : forces.keySet()) {

                if (key.startsWith("inertiaForce")) {

                    Force force = forces.get(key);

                    float friction = 2;

                    if (physicComponent != null) {

                        friction = 2;

                        //TODO: actually create the method

                    }

                    force.direction = VectorHelper.divideVectorByFloat(force.direction, friction);

                    if (Math.abs(force.direction.x) <= 0.001f &&
                            Math.abs(force.direction.y) <= 0.001f &&
                            Math.abs(force.direction.z) <= 0.001f) {

                        forces.remove(force);

                    }

                }

            }

            Vector3f forceSum = ForceController.sumForces(forces.values());

            forceSum = ForceController.getCombinedForces(forceSum);

            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            float deltaTime = TimeHelper.deltaTime;

            speed = ForceController.getSpeed(acceleration, speed, deltaTime);
            speed = VectorHelper.subtractVectors(speed, previousSpeed);
            previousSpeed = speed;

            movedSpace = ForceController.getMovedSpace(speed, deltaTime);

            if (physicComponent == null) parent.position = VectorHelper.sumVectors(new Vector3f[]{parent.position, movedSpace});

        }

    }

    public void moveInSpecificDirection(Vector3f direction) {

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void moveForward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get("forward");

        if (givenForce == null) {

            givenForce = new Force(new Vector3f(0, 0, -1));

        }


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

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void moveBackward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get("backward");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void moveLeft() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get("left");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void moveRight() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get("right");

        direction.x = -(givenForce.direction.x * (float) Math.sin(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.sin(Math.toRadians(parent.rotation.y)));
        direction.z = givenForce.direction.x * (float) Math.cos(Math.toRadians(parent.rotation.y - 90)) + givenForce.direction.z * (float) Math.cos(Math.toRadians(parent.rotation.y));

        if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(0.3f, 1, 0.3f)});

            direction.x = newDirection.x;
            direction.z = newDirection.z;

        }

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void moveUp() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get("up");

        direction.y = givenForce.direction.y;

        if (sprinting) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(1, 3, 1)});

            direction.y = newDirection.y;

        } else if (sneaking) {

            Vector3f newDirection = VectorHelper.multiplyVectors(new Vector3f[]{direction, new Vector3f(1, 0.3f, 1)});

            direction.y = newDirection.y;

        }

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void moveDown() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get("down");

        direction.y = givenForce.direction.y;

        String forceIdentifier = "inertiaForce" + forceCount;

        forces.put(forceIdentifier, new Force(direction));
        forces.get(forceIdentifier).enabled = true;

        if (forces.containsKey("inertiaForce0")) forceCount++;

        else forceCount = 0;

    }

    public void jump() {

        forces.get("jump").enabled = true;

    }

    public void sprint() {

        Controller controller = (Controller) parent.getModule(Controller.class);

        if (controller != null) {

            if (!controller.sprintModeToggle) {

                sprinting = true;
                sneaking = false;

            } else {

                sprinting = !sprinting;
                sneaking = false;

            }

        }

    }

    public void sneak() {

        Controller controller = (Controller) parent.getModule(Controller.class);


        if (controller != null)
            if (!sprinting)
                sneaking = !controller.sneakModeToggle || !sneaking;

    }

    public void rotate(float pitch, float yaw) {

        parent.rotation.x = pitch;
        parent.rotation.y = yaw;

    }

}
