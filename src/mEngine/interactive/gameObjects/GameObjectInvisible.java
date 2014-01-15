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

            Vector3f forceSum = ForceController.sumForces(forces);
            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);
            speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);

            position.x -= movedSpace.x;
            position.y += movedSpace.y;
            position.z -= movedSpace.z;

        }

    }

    public void moveForward() { forces.get(1).enabled = true; forces.get(7).enabled = true; }
    public void moveBackward() { forces.get(2).enabled = true; forces.get(8).enabled = true; }
    public void moveLeft() { forces.get(3).enabled = true; forces.get(9).enabled = true; }
    public void moveRight() { forces.get(4).enabled = true; forces.get(10).enabled = true; }
    public void moveUp() { forces.get(5).enabled = true; forces.get(11).enabled = true; }
    public void moveDown() { forces.get(6).enabled = true; forces.get(12).enabled = true; }
    public void jump() {}
    public void sprint() {}
    public void sneak() {}

}
