package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.interactive.controls.Controller;
import mEngine.physics.Force;
import mEngine.physics.ForceController;
import mEngine.util.PreferenceHelper;
import mEngine.util.TimeHelper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectMovable extends GameObject{

    protected Controller controller;
    public float mass = 0;
    public Vector3f speed = new Vector3f();

    public boolean sprinting;
    public boolean sneaking;
    public boolean continuouslyJumping;
    public boolean sneakModeToggle;
    public boolean capableOfFlying;

    public GameObjectMovable(Vector3f pos, Vector3f rot, Controller controller) {

        super(pos, rot);
        this.controller = controller;

        continuouslyJumping = PreferenceHelper.getBoolean("continuouslyJumping");
        sneakModeToggle = PreferenceHelper.getBoolean("sneakModeToggle");
        capableOfFlying = PreferenceHelper.getBoolean("capableOfFlying");

        if(mass == 0) mass = 60;

    }

    public void update() {

        updateModel();

        if(!GameController.isGamePaused) {

            //forces.get(0).enable();
            sprinting = false;
            sneaking = false;

            if(controller != null) updateController();

            for(int count = 14; count < forces.size(); count ++) {

                if(forces.get(count) != null) {

                    Force force = forces.get(count);

                    //TODO: insert a method to calculate the sliding factor of the triangle the object is moving on to calculate the force direction subtraction

                    force.direction.x /= 1.5f;
                    force.direction.y /= 1.5f;
                    force.direction.z /= 1.5f;

                    if(Math.abs(force.direction.x) <= 0.1f &&
                            Math.abs(force.direction.y) <= 0.1f &&
                            Math.abs(force.direction.z) <= 0.1f) {

                        forces.remove(force);

                    }

                }

            }

            Vector3f forceSum = ForceController.sumForces(forces);

            //TODO: complete the method that combines 3 force directions and also work with forces that operate on the y axis if the object isn't free falling

            if(forceSum.x != 0 && forceSum.z != 0) {

                Vector2f newForces = ForceController.getCombinedForces(forceSum.x, forceSum.z);

                forceSum.x = newForces.x;
                forceSum.z = newForces.y;

            }

            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / 5);
            speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime());

            //Collision Detection

            position.x -= movedSpace.x;
            position.y += movedSpace.y;
            position.z += movedSpace.z;

        }

    }

    protected void updateController() {}
    protected void updateModel() {}

    public void moveForward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(1);

        direction.x = givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveBackward() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(2);

        direction.x = givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveLeft() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(3);

        direction.x = givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveRight() {

        Vector3f direction = new Vector3f();
        Force givenForce = forces.get(4);

        direction.x = givenForce.direction.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.sin(Math.toRadians(rotation.y));
        direction.z = givenForce.direction.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + givenForce.direction.z * (float)Math.cos(Math.toRadians(rotation.y));

        forces.add(new Force(direction));
        forces.get(forces.size() - 1).enabled = true;

    }

    public void moveUp() {

        forces.get(5).enable();
        forces.get(5).setDirection(ForceController.forces.get(5).direction);
        forces.get(11).enable();

    }

    public void moveDown() {

        forces.get(6).enable();
        forces.get(6).setDirection(ForceController.forces.get(6).direction);
        forces.get(12).enable();

    }

    public void jump() { forces.get(13).enable(); }

    public void sprint() { sprinting = true; }

    public void sneak() { sneaking = true; }

    public void rotate(float pitch, float yaw) {

        rotation.x = pitch;
        rotation.y = yaw;

    }

}
