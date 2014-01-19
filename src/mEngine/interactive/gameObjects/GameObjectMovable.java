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
    public float mass;
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

        mass = 60;

    }

    public void update() {

        updateModel();

        if(!GameController.isGamePaused) {

            //forces.get(0).enable();
            sprinting = false;
            sneaking = false;

            for(Force force: forces) { force.disable(); }

            if(controller != null) updateController();

            for(Force force : forces) {

                if(force.enabled) {

                    if(forces.indexOf(force) <= 6 && forces.indexOf(force) != 0) {

                        if(Math.abs(force.direction.x) <= Math.abs(forces.get(forces.indexOf(force) + 6).direction.x) &&
                                Math.abs(force.direction.y) <= Math.abs(forces.get(forces.indexOf(force) + 6).direction.y) &&
                                Math.abs(force.direction.z) <= Math.abs(forces.get(forces.indexOf(force) + 6).direction.z)) {

                            force.setDirection(ForceController.forces.get(forces.indexOf(force)).direction);
                            force.disable();
                            forces.get(forces.indexOf(force) + 6).disable();

                        }

                    }

                }

            }

            Vector3f forceSum = ForceController.sumForces(forces);

            if(forceSum.x != 0 && forceSum.z != 0) {

                Vector2f newForces = ForceController.getCombinedForces(forceSum.x, forceSum.z);

                forceSum.x = newForces.x;
                forceSum.z = newForces.y;

            }

            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / 5);
            speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime());

            position.x -= movedSpace.x * (float)Math.sin(Math.toRadians(rotation.y - 90)) + movedSpace.z * Math.sin(Math.toRadians(rotation.y));
            position.y += movedSpace.y;
            position.z += movedSpace.x * (float)Math.cos(Math.toRadians(rotation.y - 90)) + movedSpace.z * Math.cos(Math.toRadians(rotation.y));

            //Collision Detection

        }

    }

    protected void updateController() {}
    protected void updateModel() {}

    public void moveForward() {

        forces.get(1).enable();
        forces.get(1).setDirection(ForceController.forces.get(1).direction);
        forces.get(7).enable();

    }

    public void moveBackward() {

        forces.get(2).enable();
        forces.get(2).setDirection(ForceController.forces.get(2).direction);
        forces.get(8).enable();

    }

    public void moveLeft() {

        forces.get(3).enable();
        forces.get(3).setDirection(ForceController.forces.get(3).direction);
        forces.get(9).enable();

    }

    public void moveRight() {

        forces.get(4).enable();
        forces.get(4).setDirection(ForceController.forces.get(4).direction);
        forces.get(10).enable();

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
