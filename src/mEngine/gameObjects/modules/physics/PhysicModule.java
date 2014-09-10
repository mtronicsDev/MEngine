package mEngine.gameObjects.modules.physics;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.modules.Module;
import mEngine.gameObjects.modules.renderable.RenderModule;
import mEngine.graphics.renderable.models.Face;
import mEngine.physics.collisions.Collider;
import mEngine.physics.collisions.primitives.Box;
import mEngine.physics.collisions.primitives.Triangle;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PhysicModule extends Module {

    public boolean ableToCollide = true;
    private boolean lastPhysicComponent;
    public static List<PhysicModule> physicComponents = new ArrayList<PhysicModule>();
    private static List<String> alreadyUsedModels = new ArrayList<String>();
    public Box axisAlignedBoundingBox;
    public List<Triangle> triangles;
    public MovementModule parentMovementComponent;

    public PhysicModule() {

        lastPhysicComponent = true;

        for (PhysicModule physicComponent : physicComponents)
            physicComponent.lastPhysicComponent = false;

        physicComponents.add(this);

    }

    public void onCreation(GameObject object) {

        super.onCreation(object);

        parentMovementComponent = (MovementModule) parent.getAnyComponent(MovementModule.class);

        RenderModule renderComponent = (RenderModule) parent.getAnyComponent(RenderModule.class);

        if (renderComponent == null) axisAlignedBoundingBox = new Box(parent.position, new Vector3f(2, 2, 2));

        else {

            if (alreadyUsedModels.contains(renderComponent.modelFileName)) {

                for (PhysicModule physicComponent : physicComponents) {

                    GameObject gameObject = physicComponent.parent;

                    RenderModule gameObjectRenderComponent = (RenderModule) gameObject.getAnyComponent(RenderModule.class);

                    if (gameObjectRenderComponent != null) {

                        if (gameObjectRenderComponent.modelFileName.equals(renderComponent.modelFileName)) {

                            axisAlignedBoundingBox = physicComponent.axisAlignedBoundingBox;

                            triangles = physicComponent.triangles;

                        }

                    }

                }

            } else {

                axisAlignedBoundingBox = new Box(parent.position, renderComponent.model.getSize());

                triangles = new ArrayList<Triangle>();

                for (Face face : renderComponent.model.getFaces()) {

                    triangles.add(new Triangle(renderComponent.model.getVertices().get((int) face.vertexIndices.x),
                            renderComponent.model.getVertices().get((int) face.vertexIndices.y),
                            renderComponent.model.getVertices().get((int) face.vertexIndices.z),
                            renderComponent.model.getNormals().get((int) face.normalIndices.x)));

                }

                alreadyUsedModels.add(renderComponent.modelFileName);

            }

        }

    }

    public void onUpdate() {

        if (parentMovementComponent != null)
            axisAlignedBoundingBox.position = parent.position;

        if (lastPhysicComponent) Collider.collideObjects();

    }

    public void onDestroy() {

        physicComponents.remove(this);

        if (lastPhysicComponent)
            physicComponents.get(physicComponents.size() - 1).lastPhysicComponent = true;

    }

}
