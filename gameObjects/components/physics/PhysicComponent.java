package mEngine.gameObjects.components.physics;

import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.Component;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.graphics.renderable.models.Face;
import mEngine.physics.collisions.Collider;
import mEngine.physics.collisions.primitives.Box;
import mEngine.physics.collisions.primitives.Triangle;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.rendering.ModelHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class PhysicComponent extends Component {

    public boolean ableToCollide = true;
    private boolean lastPhysicComponent;
    public static List<PhysicComponent> physicComponents = new ArrayList<PhysicComponent>();
    private static List<String> alreadyUsedModels = new ArrayList<String>();
    public Box axisAlignedBoundingBox;
    public List<Triangle> triangles;
    public MovementComponent parentMovementComponent;

    public PhysicComponent() {

        lastPhysicComponent = true;

        for (PhysicComponent physicComponent : physicComponents)
            physicComponent.lastPhysicComponent = false;

        physicComponents.add(this);

    }

    public void onCreation(GameObject object) {

        super.onCreation(object);

        parentMovementComponent = (MovementComponent) parent.getAnyComponent(MovementComponent.class);

        RenderComponent renderComponent = (RenderComponent) parent.getAnyComponent(RenderComponent.class);

        if (renderComponent == null) axisAlignedBoundingBox = new Box(parent.position, new Vector3f(2, 2, 2));

        else {

            if (alreadyUsedModels.contains(renderComponent.modelFileName)) {

                for (PhysicComponent physicComponent : physicComponents) {

                    GameObject gameObject = physicComponent.parent;

                    RenderComponent gameObjectRenderComponent = (RenderComponent) gameObject.getAnyComponent(RenderComponent.class);

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
