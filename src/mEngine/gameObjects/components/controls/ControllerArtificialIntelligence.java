package mEngine.gameObjects.components.controls;

public class ControllerArtificialIntelligence extends Controller {

    public ControllerArtificialIntelligence(float[] forceStrengths, boolean capableOFFlying) {

        super(forceStrengths, capableOFFlying);

    }

    public ControllerArtificialIntelligence(float[] forceStrengths, boolean capableOFFlying, boolean addedAsLast) {

        super(forceStrengths, capableOFFlying, addedAsLast);

    }

    public void updateObject() {
    }

}
