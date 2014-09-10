package mEngine.gameObjects.modules.particles.modules;

import mEngine.gameObjects.modules.renderable.Particle;

public abstract class ParticleComponent {

    public Particle parent;

    public void onCreation(Particle particle) {

        parent = particle;

    }

    public abstract void onUpdate();

}
