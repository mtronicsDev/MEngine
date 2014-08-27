package com.mGameLabs.mEngine.gameObjects.components.particles.particleComponents;

import com.mGameLabs.mEngine.gameObjects.components.renderable.Particle;

public abstract class ParticleComponent {

    public Particle parent;

    public void onCreation(Particle particle) {

        parent = particle;

    }

    public abstract void onUpdate();

}
