package mEngine.gameObjects.components.particles;

import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.particles.particleComponents.ParticleComponent;
import mEngine.gameObjects.components.renderable.Particle;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticleEmitter extends Component {

    public List<ParticleComponent> particleComponents = new ArrayList<ParticleComponent>();
    public Vector2f size;
    String textureName;
    int particleCount = 0;

    public ParticleEmitter(Vector2f size, String textureName) {

        this.size = size;
        this.textureName = textureName;

    }

    public ParticleEmitter addParticleComponent(ParticleComponent particleComponent) {

        particleComponents.add(particleComponent);

        return this;

    }

    public void addParticle() {

        Particle particle = new Particle(size, textureName);

        for (ParticleComponent particleComponent : particleComponents) {

            particle.addComponent(particleComponent);

        }

        parent.addComponent(particle);

        particle.onCreation(parent);

        particleCount++;

    }

}
