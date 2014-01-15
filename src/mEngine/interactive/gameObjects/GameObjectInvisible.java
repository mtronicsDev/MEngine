package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.interactive.controls.Controller;
import mEngine.physics.ForceController;
import mEngine.util.TimeHelper;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectInvisible extends GameObject {

    private Controller controller;
    Vector3f speed;
    float mass;

    public GameObjectInvisible(Vector3f pos, Vector3f rot, Controller controller) {

        super(pos, rot);
        this.controller = controller;

        speed = new Vector3f();
        mass = 60; //This is, like in GameObjectRenderable, incorrect, but it is an easy solution for a game object without a model

    }

    public void update() {

        if(!GameController.isGamePaused) {

            controller.checkInputKeys(this);

            forces.get(0).setEnabled(true);
            sprinting = false;
            sneaking = false;

            Vector3f forceSum = ForceController.sumForces(forces);
            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace;

            if(TimeHelper.currentFPS != 0) {

                movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);
                speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);

            } else {

                movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime());
                speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime());

            }

            position.x -= movedSpace.x;
            position.y += movedSpace.y;
            position.z -= movedSpace.z;

        }

    }

    public void moveForward() { forces.get(1).setEnabled(true); }
    public void moveBackward() { forces.get(2).setEnabled(true); }
    public void moveLeft() { forces.get(3).setEnabled(true); }
    public void moveRight() { forces.get(4).setEnabled(true); }
    public void moveUp() { forces.get(5).setEnabled(true); }
    public void moveDown() { forces.get(6).setEnabled(true); }
    public void jump() { forces.get(5).setEnabled(true); }
    public void sprint() { sprinting = true; }
    public void sneak() { sneaking = true; }

}
