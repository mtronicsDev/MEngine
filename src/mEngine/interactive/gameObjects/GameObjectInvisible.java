package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.interactive.controls.Controller;
import mEngine.physics.ForceController;
import mEngine.util.TimeHelper;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectInvisible extends GameObjectMovable {

    private Controller controller;
    Vector3f speed;
    float mass;

    public GameObjectInvisible(Vector3f pos, Vector3f rot, Controller controller) {

        super(pos, rot, controller);
        this.controller = controller;

    }

    public void update() {

        if(!GameController.isGamePaused) {

            controller.updateObject(this);

            Vector3f forceSum = ForceController.sumForces(forces);
            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);
            speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);

            position.x -= movedSpace.x;
            position.y += movedSpace.y;
            position.z -= movedSpace.z;

        }

    }

    public void jump() {}
    public void sprint() {}
    public void sneak() {}

}
