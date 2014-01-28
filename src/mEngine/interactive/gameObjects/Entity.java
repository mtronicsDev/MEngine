package mEngine.interactive.gameObjects;

import mEngine.interactive.controls.Controller;
import org.lwjgl.util.vector.Vector3f;

public class Entity extends GameObjectRenderable {
    
    Controller controller;
    
    public Entity(Vector3f pos, Vector3f rot, String fileName, float[] forceStrengths, Controller controller, boolean capableOfFlying) {
        
        super(pos, rot, fileName, forceStrengths, controller, capableOfFlying);
        this.controller = controller;
        
    }

    public Entity(Vector3f pos, Vector3f rot, String fileName, Controller controller, boolean capableOfFlying) {

        super(pos, rot, fileName, controller, capableOfFlying);
        this.controller = controller;

    }

    protected void updateController() { controller.updateObject(this); }

}
