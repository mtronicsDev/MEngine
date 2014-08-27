package com.mGameLabs.mEngine.physics.forces;

import org.lwjgl.util.vector.Vector3f;

public class RotationForce extends Force {

    public Vector3f pointOfAttackOnModel;

    public RotationForce(Vector3f direction, Vector3f pointOfAttackOnModel) {

        super(direction);

        this.pointOfAttackOnModel = pointOfAttackOnModel;

    }

    public Vector3f getDirectionalForceIfEnabled() {

        return new Vector3f(); //"blocking" the method of the super class because rotation forces mustn't be interpreted by the Force Controller

    }

}
