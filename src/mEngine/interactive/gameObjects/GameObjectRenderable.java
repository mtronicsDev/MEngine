package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.graphics.renderable.Model;
import mEngine.physics.Force;
import mEngine.physics.ForceController;
import mEngine.util.TimeHelper;
import org.lwjgl.util.vector.Vector3f;

public class GameObjectRenderable extends GameObject {

    Model model;
    float mass;
    Vector3f speed;

    public GameObjectRenderable(Vector3f pos, Vector3f rot, String modelFileName, String textureFileName) {

        super(pos, rot);
        model = new Model(modelFileName, textureFileName, pos, rot);
        mass = 60; //This is not correct, I will implement a method for defining the weight of the model later
        speed = new Vector3f();

    }

    public void update() {

        model.update(position, rotation);

        if(!GameController.isGamePaused) {

            updateEntity();

            //forces.get(0).enabled = true;
            sprinting = false;
            sneaking = false;

            for(Force force : forces) {

                if(force.enabled) {

                    if(forces.indexOf(force) <= 6 && forces.indexOf(force) != 0) {

                        force.direction.x /= 2;
                        force.direction.z /= 2;

                        if(Math.abs(force.direction.x) <= Math.abs(forces.get(forces.indexOf(force) + 6).direction.x) && Math.abs(force.direction.y) <= Math.abs(forces.get(forces.indexOf(force) + 6).direction.y) && Math.abs(force.direction.z) <= Math.abs(forces.get(forces.indexOf(force) + 6).direction.z)) {

                            force.setDirection(ForceController.forces.get(forces.indexOf(force)).direction);
                            force.enabled = false;
                            forces.get(forces.indexOf(force) + 6).enabled = false;

                        }

                    }

                }

            }

            Vector3f forceSum = ForceController.sumForces(forces);
            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / 5);
            speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime());

            position.x -= movedSpace.x;
            position.y += movedSpace.y;
            position.z += movedSpace.z;

            //Collision Detection

        }

    }

    public void updateEntity() {};

    public void moveForward() {

        forces.get(1).enabled = true;
        forces.get(1).setDirection(ForceController.forces.get(1).direction);
        forces.get(7).enabled = true;

    }

    public void moveBackward() {

        forces.get(2).enabled = true;
        forces.get(2).setDirection(ForceController.forces.get(2).direction);
        forces.get(8).enabled = true;

    }

    public void moveLeft() {

        forces.get(3).enabled = true;
        forces.get(3).setDirection(ForceController.forces.get(3).direction);
        forces.get(9).enabled = true;

    }

    public void moveRight() {

        forces.get(4).enabled = true;
        forces.get(4).setDirection(ForceController.forces.get(4).direction);
        forces.get(10).enabled = true;

    }

    public void moveUp() {

        forces.get(5).enabled = true;
        forces.get(5).setDirection(ForceController.forces.get(5).direction);
        forces.get(11).enabled = true;

    }

    public void moveDown() {

        forces.get(6).enabled = true;
        forces.get(6).setDirection(ForceController.forces.get(6).direction);
        forces.get(12).enabled = true;

    }

    public void jump() {

        forces.get(13).enabled = true;

    }

    public void sprint() {

        sprinting = true;

    }

    public void sneak() {

        sneaking = true;

    }

}
