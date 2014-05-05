package mEngine.gameObjects.components.particles.particleComponents;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.graphics.Renderer;
import mEngine.util.data.BinaryHelper;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

public class MovementParticleComponent extends ParticleComponent {

    Vector3f rotationAbility;

    public MovementParticleComponent(boolean xAxisRotation, boolean yAxisRotation, boolean zAxisRotation) {

        rotationAbility = new Vector3f(BinaryHelper.convertToBinaryInteger(xAxisRotation),
                BinaryHelper.convertToBinaryInteger(yAxisRotation), BinaryHelper.convertToBinaryInteger(zAxisRotation));

    }

    public void onUpdate() {

        Vector3f previousNormal = new Vector3f(parent.normal);

        Camera camera = null;

        if (Renderer.currentRenderQueue.camera != null) camera = Renderer.currentRenderQueue.camera;

        else {

            for (GameObject object : ObjectController.gameObjects) {

                for (Component component : object.components.values()) {

                    if (component instanceof  Camera) camera = (Camera) component;

                }

            }

        }

        Vector3f cameraPosition = camera.position;

        parent.normal = VectorHelper.normalizeVector(VectorHelper.subtractVectors(cameraPosition, parent.position));

        parent.calculateVertices(previousNormal);

    }

}
