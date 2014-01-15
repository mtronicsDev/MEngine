package mEngine.interactive.gameObjects;

import mEngine.core.GameController;
import mEngine.graphics.renderable.Model;
import mEngine.physics.Force;
import mEngine.physics.ForceController;
import mEngine.util.TimeHelper;
import org.lwjgl.util.vector.Vector3f;

import java.sql.Time;

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

            Vector3f forceSum = ForceController.sumForces();
            Vector3f acceleration = ForceController.getAcceleration(forceSum, mass);

            Vector3f movedSpace;
            if(TimeHelper.currentFPS != 0){

                movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);
                speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime() / TimeHelper.currentFPS);

            }
            else {

                movedSpace = ForceController.getMovedSpace(acceleration, speed, TimeHelper.deltaTime());
                speed = ForceController.getSpeed(acceleration, speed, TimeHelper.deltaTime());

            }

            //Collision Detection

        }

    }

    public void updateEntity() {};

}
