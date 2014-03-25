package mEngine.util.rendering;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.RenderComponent;
import mEngine.graphics.renderable.Face;
import mEngine.graphics.renderable.Model;
import org.lwjgl.util.vector.Vector3f;

public class RenderHelper {

    public static boolean isFaceNeededToBeRendered(Face face) {

        boolean neededToBeRendered = false;

        Model model = null;

        for(GameObject obj : ObjectController.gameObjects) {

            boolean faceFound = false;
            RenderComponent renderComponent = (RenderComponent)obj.components.get("renderComponent");

            if(renderComponent != null) {

                for(Face faceListPart : renderComponent.model.faces) {

                    if(faceListPart == face) {

                        model = renderComponent.model;
                        break;

                    }

                }

            }

            if(faceFound) break;

        }

        if(model != null) {

            Vector3f vertexA = model.vertices.get((int)face.vertexIndices.x);
            Vector3f vertexB = model.vertices.get((int)face.vertexIndices.y);
            Vector3f vertexC = model.vertices.get((int)face.vertexIndices.z);

            if(isVectorOnScreen(vertexA) && isVectorOnScreen(vertexB) && isVectorOnScreen(vertexC)) {

                neededToBeRendered = true;

            }

        }

        return neededToBeRendered;

    }

    public static boolean isVectorOnScreen(Vector3f vector) {

        boolean vectorOnScreen = false;

        if(isVectorTheoreticallyOnScreen(vector)) {

            vectorOnScreen = true;

        }

        return vectorOnScreen;

    }

    public static boolean isVectorTheoreticallyOnScreen(Vector3f vector) {

        boolean theoreticallyOnScreen = true;

        return theoreticallyOnScreen;

    }

}
