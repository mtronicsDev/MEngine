package mEngine.gameObjects.components.controller;

import java.io.Serializable;

public class ControllerArtificialIntelligence extends Controller implements Serializable {

    public ControllerArtificialIntelligence(float[] forceStrengths, boolean capableOFFlying) {

        super(forceStrengths, capableOFFlying);

    }

    public void updateObject() {
    }

}
