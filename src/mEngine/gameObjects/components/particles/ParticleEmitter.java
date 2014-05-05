package mEngine.gameObjects.components.particles;

import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.particles.particleComponents.ParticleComponent;
import mEngine.gameObjects.components.renderable.Particle;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class ParticleEmitter extends Component {

    public Map<String, ParticleComponent> particleComponents = new HashMap<String, ParticleComponent>();
    public Vector2f size;
    String textureName;
    int particleCount = 0;

    public ParticleEmitter(Vector2f size, String textureName) {

        this.size = size;
        this.textureName = textureName;

    }

    public ParticleEmitter addParticleComponent(String componentName, ParticleComponent particleComponent) {

        particleComponents.put(componentName, particleComponent);

        return this;

    }

    public void addParticle() {

        Particle particle = new Particle(size, textureName);

        for (String key : particleComponents.keySet()) {

            particle.addComponent(key, particleComponents.get(key));

        }

        String particleIdentifier = "particle" + particleCount;

        parent.addComponent(particleIdentifier, particle);

        parent.getComponent(particleIdentifier).onCreation(parent);

        particleCount++;

    }

}
