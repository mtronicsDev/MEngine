package mEngine.util.rendering;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.renderable.Camera;
import mEngine.gameObjects.components.renderable.RenderComponent;
import mEngine.graphics.renderable.models.Face;
import mEngine.graphics.renderable.models.Model;
import mEngine.util.math.vectors.Matrix3d;
import mEngine.util.math.vectors.VectorHelper;
import mEngine.util.resources.PreferenceHelper;
import org.lwjgl.util.vector.Vector3f;

public class RenderHelper {

    public static boolean isFaceNeededToBeRendered(Face face) {

        boolean neededToBeRendered = false;

        Model model = null;

        for (GameObject obj : ObjectController.gameObjects) {

            boolean faceFound = false;
            RenderComponent renderComponent = (RenderComponent) obj.components.get("renderComponent");

            if (renderComponent != null) {

                for (Face faceListPart : renderComponent.model.faces) {

                    if (faceListPart == face) {

                        model = renderComponent.model;
                        faceFound = true;
                        break;

                    }

                }

            }

            if (faceFound) break;

        }

        if (model != null) {

            Vector3f vertexA = VectorHelper.sumVectors(new Vector3f[]{model.vertices.get((int) face.vertexIndices.x), model.position});
            Vector3f vertexB = VectorHelper.sumVectors(new Vector3f[]{model.vertices.get((int) face.vertexIndices.y), model.position});
            Vector3f vertexC = VectorHelper.sumVectors(new Vector3f[]{model.vertices.get((int) face.vertexIndices.z), model.position});

            if (isVectorOnScreen(vertexA) || isVectorOnScreen(vertexB) || isVectorOnScreen(vertexC)) {

                neededToBeRendered = true;

            }

        }

        return neededToBeRendered;

    }

    public static boolean isVectorOnScreen(Vector3f vector) {

        boolean vectorOnScreen = false;

        if (isVectorTheoreticallyOnScreen(vector)) {

            vectorOnScreen = true;

        }

        return vectorOnScreen;

    }

    public static boolean isVectorTheoreticallyOnScreen(Vector3f vector) {

        boolean theoreticallyOnScreen = false;

        Camera camera = null;

        for (GameObject obj : ObjectController.gameObjects) {

            Camera maybeCamera = (Camera) obj.getComponent("camera");

            if (maybeCamera != null) {

                camera = maybeCamera;
                break;

            }

        }

        if (camera != null) {

            vector = VectorHelper.subtractVectors(vector, camera.position);
            vector = VectorHelper.normalizeVector(vector);

            float fov = (float) Math.toRadians(PreferenceHelper.getFloat("fieldOfView"));

            Vector3f xAxis = new Vector3f(1, 0, 0);
            Vector3f yAxis = new Vector3f(0, 1, 0);
            Vector3f zAxis = new Vector3f(0, 0, 1);

            Matrix3d yAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(-fov / 2), 0, (float) Math.sin(-fov / 2)),
                    new Vector3f(0, 1, 0),
                    new Vector3f(-(float) Math.sin(-fov / 2), 0, (float) Math.cos(-fov / 2)));

            Vector3f leftLowerCorner = yAxisRotationMatrix.multiplyByVector(zAxis);

            yAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(fov / 2), 0, (float) Math.sin(fov / 2)),
                    new Vector3f(0, 1, 0),
                    new Vector3f(-(float) Math.sin(fov / 2), 0, (float) Math.cos(fov / 2)));

            Vector3f rightUpperCorner = yAxisRotationMatrix.multiplyByVector(zAxis);

            Matrix3d xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                    new Vector3f(0, (float) Math.cos(fov / 2), -(float) Math.sin(fov / 2)),
                    new Vector3f(0, (float) Math.sin(fov / 2), (float) Math.cos(fov / 2)));

            leftLowerCorner = xAxisRotationMatrix.multiplyByVector(leftLowerCorner);

            xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                    new Vector3f(0, (float) Math.cos(-fov / 2), -(float) Math.sin(-fov / 2)),
                    new Vector3f(0, (float) Math.sin(-fov / 2), (float) Math.cos(-fov / 2)));

            rightUpperCorner = xAxisRotationMatrix.multiplyByVector(rightUpperCorner);

            Vector3f alphaIndicator = new Vector3f(camera.percentRotation.x, 0, camera.percentRotation.z);
            float alpha = VectorHelper.getAngle(zAxis, alphaIndicator);

            if (VectorHelper.getScalarProduct(camera.percentRotation, xAxis) < 0) alpha = -alpha;

            yAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(alpha), 0, (float) Math.sin(alpha)),
                    new Vector3f(0, 1, 0),
                    new Vector3f(-(float) Math.sin(alpha), 0, (float) Math.cos(alpha)));

            vector = yAxisRotationMatrix.multiplyByVector(vector);

            if (camera.percentRotation.y != 0) {

                Vector3f betaIndicator = new Vector3f(0, camera.percentRotation.y, 0);
                float beta = VectorHelper.getAngle(zAxis, betaIndicator);

                if (VectorHelper.getScalarProduct(camera.percentRotation, yAxis) < 0) beta = -beta;

                xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                        new Vector3f(0, (float) Math.cos(beta), -(float) Math.sin(beta)),
                        new Vector3f(0, (float) Math.sin(beta), (float) Math.cos(beta)));

                vector = xAxisRotationMatrix.multiplyByVector(vector);

            }

            theoreticallyOnScreen = vector.x >= leftLowerCorner.x && vector.x <= rightUpperCorner.x && vector.y >= leftLowerCorner.y && vector.y <= rightUpperCorner.y;

        }

        return theoreticallyOnScreen;

    }

}
