package mEngine.physics.collisions;

import mEngine.core.ObjectController;
import mEngine.gameObjects.GameObject;
import mEngine.gameObjects.components.CollideComponent;
import mEngine.gameObjects.components.controls.Controller;
import mEngine.gameObjects.components.MovementComponent;
import mEngine.gameObjects.components.rendering.RenderComponent;
import mEngine.graphics.renderable.Face;
import mEngine.physics.collisions.primitives.Box;
import mEngine.physics.collisions.primitives.Plane;
import mEngine.util.math.vectors.Matrix3d;
import mEngine.util.math.vectors.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Collider {

    public static boolean areAABBsColliding(GameObject objA, GameObject objB) {

        boolean colliding;

        RenderComponent renderComponentA = (RenderComponent) objA.getComponent("renderComponent");
        CollideComponent collideComponentA = (CollideComponent) objA.getComponent("collideComponent");

        RenderComponent renderComponentB = (RenderComponent) objB.getComponent("renderComponent");
        CollideComponent collideComponentB = (CollideComponent) objB.getComponent("collideComponent");

        if (renderComponentA != null && renderComponentB != null && collideComponentA != null && collideComponentB != null) {

            Box boxA = new Box(VectorHelper.subtractVectors(objA.position, VectorHelper.divideVectorByFloat(renderComponentA.model.getSize(), 2)), renderComponentA.model.getSize());
            Box boxB = new Box(VectorHelper.subtractVectors(objB.position, VectorHelper.divideVectorByFloat(renderComponentB.model.getSize(), 2)), renderComponentB.model.getSize());

            colliding = areBoxesColliding(boxA, boxB);

        } else colliding = false;

        return colliding;

    }

    public static boolean areBoxesColliding(Box boxA, Box boxB) {

        return boxA.position.x < boxB.position.x + boxB.size.x
                && boxA.position.x + boxA.size.x > boxB.position.x
                && boxA.position.y < boxB.position.y + boxB.size.y
                && boxA.position.y + boxA.size.y > boxB.position.y
                && boxA.position.z < boxB.position.z + boxB.size.z
                && boxA.position.z + boxA.size.z > boxB.position.z;

    }

    public static boolean isBoxCollidingWithAABB(Box boxA, GameObject obj) {

        boolean colliding;

        RenderComponent renderComponent = (RenderComponent) obj.getComponent("renderComponent");
        CollideComponent collideComponent = (CollideComponent) obj.getComponent("collideComponent");

        if (renderComponent != null && collideComponent != null) {

            Box boxB = new Box(VectorHelper.subtractVectors(obj.position, VectorHelper.divideVectorByFloat(renderComponent.model.getSize(), 2)),
                    renderComponent.model.getSize());

            colliding = areBoxesColliding(boxA, boxB);

        } else colliding = false;

        return colliding;

    }

    public static boolean isCollidingWithSomething(GameObject objA) {

        boolean colliding = false;
        List<Face> allFaces = new ArrayList<Face>();
        List<Vector3f> allVertices = new ArrayList<Vector3f>();
        List<Vector3f> allNormals = new ArrayList<Vector3f>();
        boolean maybeColliding = false;
        RenderComponent renderComponentA = (RenderComponent) objA.getComponent("renderComponent");
        CollideComponent collideComponentA = (CollideComponent) objA.getComponent("collideComponent");

        for (int i = 0; i < ObjectController.gameObjects.size(); i++) {

            GameObject objB = ObjectController.getGameObject(i);

            if (objA != objB) {

                RenderComponent renderComponentB = (RenderComponent) objB.getComponent("renderComponent");
                CollideComponent collideComponentB = (CollideComponent) objB.getComponent("collideComponent");

                if (renderComponentB != null && collideComponentB != null && renderComponentA != null && collideComponentA != null) {

                    if (areAABBsColliding(objA, objB)) {

                        for (Face face : renderComponentB.model.faces) {

                            allFaces.add(new Face(face));

                            Face newFace = allFaces.get(allFaces.size() - 1);

                            allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.x), objB.position})));
                            allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.y), objB.position})));
                            allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.z), objB.position})));

                            allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) face.normalIndices.x)));
                            allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) face.normalIndices.y)));
                            allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) face.normalIndices.z)));

                            newFace.vertexIndices.x = allVertices.size() - 3;
                            newFace.vertexIndices.y = allVertices.size() - 2;
                            newFace.vertexIndices.z = allVertices.size() - 1;

                            newFace.normalIndices.x = allNormals.size() - 3;
                            newFace.normalIndices.y = allNormals.size() - 2;
                            newFace.normalIndices.z = allNormals.size() - 1;

                        }

                        maybeColliding = true;

                    }

                }

            }

        }

        if (maybeColliding) {

            for (Face faceA : renderComponentA.model.faces) {

                Vector3f maxVertexPos;
                Vector3f minVertexPos;
                Vector3f middle;
                Vector3f radius;
                List<Vector3f> verticesA = new ArrayList<Vector3f>();
                List<Vector3f> verticesToCalculate = new ArrayList<Vector3f>();

                verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentA.model.vertices.get((int) faceA.vertexIndices.x), objA.position})));
                verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentA.model.vertices.get((int) faceA.vertexIndices.y), objA.position})));
                verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentA.model.vertices.get((int) faceA.vertexIndices.z), objA.position})));

                verticesToCalculate.add(new Vector3f());
                verticesToCalculate.add(VectorHelper.subtractVectors(verticesA.get(1), verticesA.get(0)));
                verticesToCalculate.add(VectorHelper.subtractVectors(verticesA.get(2), verticesA.get(0)));

                maxVertexPos = new Vector3f();
                minVertexPos = new Vector3f();

                for (Vector3f vertex : verticesToCalculate) {

                    if (vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
                    else if (vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

                    if (vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
                    else if (vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

                    if (vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
                    else if (vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

                }

                maxVertexPos = VectorHelper.sumVectors(new Vector3f[]{maxVertexPos, verticesA.get(0)});
                minVertexPos = VectorHelper.sumVectors(new Vector3f[]{minVertexPos, verticesA.get(0)});

                middle = VectorHelper.divideVectors(VectorHelper.sumVectors(new Vector3f[]{minVertexPos, maxVertexPos}), new Vector3f(2f, 2f, 2f));

                radius = VectorHelper.subtractVectors(maxVertexPos, middle);

                Matrix3d changeOfBasisMatrix = new Matrix3d(new Vector3f(1 / radius.x, 0, 0), new Vector3f(0, 1 / radius.y, 0), new Vector3f(0, 0, 1 / radius.z));

                middle = changeOfBasisMatrix.multiplyByVector(middle);

                for (Face faceB : allFaces) {

                    boolean collidingHere = false;

                    Vector3f normal = new Vector3f(allNormals.get((int) faceB.normalIndices.x));

                    Vector3f vertexA = new Vector3f(allVertices.get((int) faceB.vertexIndices.x));
                    vertexA = changeOfBasisMatrix.multiplyByVector(vertexA);

                    Vector3f vertexB = new Vector3f(allVertices.get((int) faceB.vertexIndices.y));
                    vertexB = changeOfBasisMatrix.multiplyByVector(vertexB);

                    Vector3f vertexC = new Vector3f(allVertices.get((int) faceB.vertexIndices.z));
                    vertexC = changeOfBasisMatrix.multiplyByVector(vertexC);

                    Vector3f intersectionPoint = VectorHelper.subtractVectors(middle, normal);

                    float longestVertexDifference = VectorHelper.getAbs(VectorHelper.subtractVectors(vertexB, vertexA));
                    if (VectorHelper.getAbs(VectorHelper.subtractVectors(vertexC, vertexA)) > longestVertexDifference)
                        longestVertexDifference = VectorHelper.getAbs(VectorHelper.subtractVectors(vertexC, vertexA));

                    if (VectorHelper.getAbs(VectorHelper.subtractVectors(intersectionPoint, vertexA)) <= longestVertexDifference) {

                        //Special thanks to Mike Ganshorn for this piece of code
                        float difference = Math.abs(VectorHelper.getScalarProduct(normal, intersectionPoint) + VectorHelper.getScalarProduct(normal, vertexA));

                        if (difference <= 1) collidingHere = true;

                    }

                    if (collidingHere) colliding = true;

                    else {

                        Vector3f differenceToVertices = new Vector3f(VectorHelper.getAbs(VectorHelper.subtractVectors(vertexA, middle)) - 1,
                                VectorHelper.getAbs(VectorHelper.subtractVectors(vertexB, middle)) - 1,
                                VectorHelper.getAbs(VectorHelper.subtractVectors(vertexC, middle)) - 1);

                        if (differenceToVertices.x >= 0 && differenceToVertices.x <= 1) collidingHere = true;

                        else if (differenceToVertices.y >= 0 && differenceToVertices.y <= 1) collidingHere = true;

                        else if (differenceToVertices.z >= 0 && differenceToVertices.z <= 1) collidingHere = true;

                        if (collidingHere) colliding = true;

                    }

                }

            }

        }

        return colliding;

    }

    public static void collideObject(GameObject objA) {

        RenderComponent renderComponentA = (RenderComponent) objA.getComponent("renderComponent");
        CollideComponent collideComponentA = (CollideComponent) objA.getComponent("collideComponent");
        MovementComponent movementComponentA = (MovementComponent) objA.getComponent("movementComponent");
        Vector3f velocity = new Vector3f(movementComponentA.movedSpace);
        Vector3f movedSpace = new Vector3f();
        List<Face> allFaces = new ArrayList<Face>();
        List<Vector3f> allVertices = new ArrayList<Vector3f>();
        List<Vector3f> allNormals = new ArrayList<Vector3f>();
        List<Face> objAFaces = new ArrayList<Face>();
        List<Vector3f> objAVertices = new ArrayList<Vector3f>();
        List<Vector3f> objANormals = new ArrayList<Vector3f>();
        boolean maybeColliding = false;
        boolean colliding = false;

        List<Box> collisionBoxesA = new ArrayList<Box>();
        Box collisionBoxA = VectorHelper.getAABB(objA);

        float[] collisionTimes = null;
        Face[] collisionFaces = null;

        if (renderComponentA != null) {

            for (GameObject objB : ObjectController.gameObjects) {

                if (objA != objB) {


                }

            }

            collisionTimes = new float[renderComponentA.model.faces.size()];
            collisionFaces = new Face[renderComponentA.model.faces.size()];

        }

        for (int count = 0; count < collisionTimes.length; count++) collisionTimes[count] = 2;

        float finalCollisionTime = 2;
        Face finalCollisionFace = null;
        List<GameObject> objList = new ArrayList<GameObject>();
        GameObject collidingObject = null;

        for (GameObject objB : ObjectController.gameObjects) {

            if (objA != objB) {

                RenderComponent renderComponentB = (RenderComponent) objB.getComponent("renderComponent");
                CollideComponent collideComponentB = (CollideComponent) objB.getComponent("collideComponent");

                if (renderComponentA != null && renderComponentB != null && collideComponentB != null) {

                    if (isBoxCollidingWithAABB(collisionBoxA, objB)) {

                        /*List<Box> smallerCollisionBoxes = new ArrayList<Box>();
                        Vector3f smallerBoxSize = VectorHelper.divideVectorByFloat(VectorHelper.getAABB(objB).size, 2);
                        Vector3f standardPosition = VectorHelper.subtractVectors(objB.position, VectorHelper.divideVectorByFloat(VectorHelper.getAABB(objB).size, 2));

                        smallerCollisionBoxes.add(new Box(standardPosition, smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition, new Vector3f(VectorHelper.divideVectorByFloat(smallerBoxSize, 2).x, 0, 0)}), smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition, new Vector3f(0, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).y, 0)}), smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition, new Vector3f(0, 0, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).z)}), smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition,
                                        new Vector3f(VectorHelper.divideVectorByFloat(smallerBoxSize, 2).x, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).y, 0)}), smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition,
                                        new Vector3f(VectorHelper.divideVectorByFloat(smallerBoxSize, 2).x, 0, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).z)}), smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition,
                                        new Vector3f(0, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).y, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).z)}), smallerBoxSize));
                        smallerCollisionBoxes.add(new Box(VectorHelper.sumVectors(new Vector3f[]
                                {standardPosition,
                                        new Vector3f(VectorHelper.divideVectorByFloat(smallerBoxSize, 2).x, VectorHelper.divideVectorByFloat(smallerBoxSize, 2).y,
                                                VectorHelper.divideVectorByFloat(smallerBoxSize, 2).z)}), smallerBoxSize));

                        boolean[] areSmallerBoxesColliding = new boolean[8];

                        for (Box smallerCollisionBox : smallerCollisionBoxes) {

                            if (areBoxesColliding(collisionBoxA, smallerCollisionBox))
                                areSmallerBoxesColliding[smallerCollisionBoxes.indexOf(smallerCollisionBox)] = true;

                            else areSmallerBoxesColliding[smallerCollisionBoxes.indexOf(smallerCollisionBox)] = false;

                        }

                        if (areSmallerBoxesColliding.equals(new boolean[]{true, true, true, true, true, true, true, true})) {*/

                        for (Face faceB : renderComponentB.model.faces) {

                            allFaces.add(new Face(faceB));
                            objList.add(objB);

                            Face newFace = allFaces.get(allFaces.size() - 1);

                            allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                    {renderComponentB.model.vertices.get((int) faceB.vertexIndices.x), objB.position})));
                            allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                    {renderComponentB.model.vertices.get((int) faceB.vertexIndices.y), objB.position})));
                            allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                    {renderComponentB.model.vertices.get((int) faceB.vertexIndices.z), objB.position})));

                            allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.x)));
                            allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.y)));
                            allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.z)));

                            newFace.vertexIndices.x = allVertices.size() - 3;
                            newFace.vertexIndices.y = allVertices.size() - 2;
                            newFace.vertexIndices.z = allVertices.size() - 1;

                            newFace.normalIndices.x = allNormals.size() - 3;
                            newFace.normalIndices.y = allNormals.size() - 2;
                            newFace.normalIndices.z = allNormals.size() - 1;

                        }

                        /*} else {

                            for (Face faceB : renderComponentB.model.faces) {

                                allFaces.add(new Face(faceB));
                                objList.add(objB);

                                Face newFace = allFaces.get(allFaces.size() - 1);

                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                        {renderComponentB.model.vertices.get((int) faceB.vertexIndices.x), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                        {renderComponentB.model.vertices.get((int) faceB.vertexIndices.y), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                        {renderComponentB.model.vertices.get((int) faceB.vertexIndices.z), objB.position})));

                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.x)));
                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.y)));
                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.z)));

                                newFace.vertexIndices.x = allVertices.size() - 3;
                                newFace.vertexIndices.y = allVertices.size() - 2;
                                newFace.vertexIndices.z = allVertices.size() - 1;

                                newFace.normalIndices.x = allNormals.size() - 3;
                                newFace.normalIndices.y = allNormals.size() - 2;
                                newFace.normalIndices.z = allNormals.size() - 1;

                            } */

                            /*List<Box> collisionBoxesB = new ArrayList<Box>();


                            for (Box collisionBoxB : collisionBoxesB) {

                                for (Face faceB : renderComponentB.model.faces) {

                                    List<Vector3f> verticesB = new ArrayList<Vector3f>();
                                    verticesB.add(new Vector3f(renderComponentB.model.vertices.get((int) faceB.vertexIndices.x)));
                                    verticesB.add(new Vector3f(renderComponentB.model.vertices.get((int) faceB.vertexIndices.y)));
                                    verticesB.add(new Vector3f(renderComponentB.model.vertices.get((int) faceB.vertexIndices.z)));

                                    Vector3f normalB = renderComponentB.model.normals.get((int) faceB.normalIndices.x);

                                    if (VectorHelper.isTriangleInsideBox(new Triangle(verticesB.get(0), normalB, verticesB.get(1), verticesB.get(2)), collisionBoxB)) {

                                        allFaces.add(new Face(faceB));
                                        objList.add(objB);

                                        Face newFace = allFaces.get(allFaces.size() - 1);

                                        allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                                {renderComponentB.model.vertices.get((int) faceB.vertexIndices.x), objB.position})));
                                        allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                                {renderComponentB.model.vertices.get((int) faceB.vertexIndices.y), objB.position})));
                                        allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]
                                                {renderComponentB.model.vertices.get((int) faceB.vertexIndices.z), objB.position})));

                                        allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.x)));
                                        allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.y)));
                                        allNormals.add(new Vector3f(renderComponentB.model.normals.get((int) faceB.normalIndices.z)));

                                        newFace.vertexIndices.x = allVertices.size() - 3;
                                        newFace.vertexIndices.y = allVertices.size() - 2;
                                        newFace.vertexIndices.z = allVertices.size() - 1;

                                        newFace.normalIndices.x = allNormals.size() - 3;
                                        newFace.normalIndices.y = allNormals.size() - 2;
                                        newFace.normalIndices.z = allNormals.size() - 1;

                                    }

                                }

                            }

                        } */

                        maybeColliding = true;

                    }

                }

            }

        }

        if (maybeColliding) {

            List<Vector3f> middles = new ArrayList<Vector3f>();
            List<Vector3f> radii = new Vector<Vector3f>();

            for (Face faceA : renderComponentA.model.faces) {

                Vector3f maxVertexPos;
                Vector3f minVertexPos;
                List<Vector3f> verticesA = new ArrayList<Vector3f>();
                List<Vector3f> verticesToCalculate = new ArrayList<Vector3f>();

                verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentA.model.vertices.get((int) faceA.vertexIndices.x), objA.position})));
                verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentA.model.vertices.get((int) faceA.vertexIndices.y), objA.position})));
                verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentA.model.vertices.get((int) faceA.vertexIndices.z), objA.position})));

                verticesToCalculate.add(new Vector3f());
                verticesToCalculate.add(new Vector3f(VectorHelper.subtractVectors(verticesA.get(1), verticesA.get(0))));
                verticesToCalculate.add(new Vector3f(VectorHelper.subtractVectors(verticesA.get(2), verticesA.get(0))));

                maxVertexPos = new Vector3f();
                minVertexPos = new Vector3f();

                for (Vector3f vertex : verticesToCalculate) {

                    if (vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
                    else if (vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

                    if (vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
                    else if (vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

                    if (vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
                    else if (vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

                }

                maxVertexPos = VectorHelper.sumVectors(new Vector3f[]{maxVertexPos, verticesA.get(0)});
                minVertexPos = VectorHelper.sumVectors(new Vector3f[]{minVertexPos, verticesA.get(0)});

                middles.add(VectorHelper.divideVectorByFloat(VectorHelper.sumVectors(new Vector3f[]{minVertexPos, maxVertexPos}), 2));
                Vector3f middle = middles.get(middles.size() - 1);

                radii.add(VectorHelper.subtractVectors(maxVertexPos, middle));
                Vector3f radius = radii.get(radii.size() - 1);

                Matrix3d changeOfBasisMatrix = new Matrix3d(new Vector3f(1 / radius.x, 0, 0), new Vector3f(0, 1 / radius.y, 0), new Vector3f(0, 0, 1 / radius.z));
                Matrix3d invertedChangeOfBasisMatrix = new Matrix3d(new Vector3f(radius.x, 0, 0), new Vector3f(0, radius.y, 0), new Vector3f(0, 0, radius.z));

                middle = changeOfBasisMatrix.multiplyByVector(middle);
                velocity = changeOfBasisMatrix.multiplyByVector(velocity);

                for (Face faceB : allFaces) {

                    boolean collidingHere = false;

                    Vector3f normal = new Vector3f(allNormals.get((int) faceB.normalIndices.x));

                    Vector3f vertexA = new Vector3f(allVertices.get((int) faceB.vertexIndices.x));
                    vertexA = changeOfBasisMatrix.multiplyByVector(vertexA);

                    Vector3f vertexB = new Vector3f(allVertices.get((int) faceB.vertexIndices.y));
                    vertexB = changeOfBasisMatrix.multiplyByVector(vertexB);

                    Vector3f vertexC = new Vector3f(allVertices.get((int) faceB.vertexIndices.z));
                    vertexC = changeOfBasisMatrix.multiplyByVector(vertexC);

                    Vector3f intersectionPoint = VectorHelper.subtractVectors(middle, normal);

                    float longestVertexDifference = VectorHelper.getAbs(VectorHelper.subtractVectors(vertexB, vertexA));
                    if (VectorHelper.getAbs(VectorHelper.subtractVectors(vertexC, vertexA)) > longestVertexDifference)
                        longestVertexDifference = VectorHelper.getAbs(VectorHelper.subtractVectors(vertexC, vertexA));

                    if (VectorHelper.getAbs(VectorHelper.subtractVectors(intersectionPoint, vertexA)) <= longestVertexDifference) {

                        //Special thanks to Mike Ganshorn for this piece of code
                        float difference = VectorHelper.getDifferenceBetweenPlaneAndVector(new Plane(vertexA, normal), intersectionPoint);

                        float collisionTime = difference / VectorHelper.getAbs(velocity);

                        if (collisionTime >= 0 && collisionTime <= 1 && Math.abs(collisionTime) < Math.abs(collisionTimes[renderComponentA.model.faces.indexOf(faceA)])) {

                            collidingHere = true;
                            collisionTimes[renderComponentA.model.faces.indexOf(faceA)] = collisionTime;
                            collisionFaces[renderComponentA.model.faces.indexOf(faceA)] = faceB;

                        }

                    }

                    if (collidingHere) colliding = true;

                    else {

                        Vector3f differenceToVertices = new Vector3f(VectorHelper.getAbs(VectorHelper.subtractVectors(vertexA, middle)) - 1,
                                VectorHelper.getAbs(VectorHelper.subtractVectors(vertexB, middle)) - 1,
                                VectorHelper.getAbs(VectorHelper.subtractVectors(vertexC, middle)) - 1);

                        Vector3f collisionTime = VectorHelper.divideVectors(differenceToVertices,
                                new Vector3f(VectorHelper.getAbs(velocity), VectorHelper.getAbs(velocity), VectorHelper.getAbs(velocity)));

                        if (collisionTime.x >= 0 && collisionTime.x <= 1 && Math.abs(collisionTime.x) < Math.abs(collisionTimes[renderComponentA.model.faces.indexOf(faceA)])) {

                            collidingHere = true;
                            collisionTimes[renderComponentA.model.faces.indexOf(faceA)] = collisionTime.x;
                            collisionFaces[renderComponentA.model.faces.indexOf(faceA)] = faceB;

                        } else if (collisionTime.y >= 0 && collisionTime.y <= 1 && Math.abs(collisionTime.y) < Math.abs(collisionTimes[renderComponentA.model.faces.indexOf(faceA)])) {

                            collidingHere = true;
                            collisionTimes[renderComponentA.model.faces.indexOf(faceA)] = collisionTime.y;
                            collisionFaces[renderComponentA.model.faces.indexOf(faceA)] = faceB;

                        } else if (collisionTime.z >= 0 && collisionTime.z <= 1 && Math.abs(collisionTime.z) < Math.abs(collisionTimes[renderComponentA.model.faces.indexOf(faceA)])) {

                            collidingHere = true;
                            collisionTimes[renderComponentA.model.faces.indexOf(faceA)] = collisionTime.z;
                            collisionFaces[renderComponentA.model.faces.indexOf(faceA)] = faceB;

                        }

                        if (collidingHere) colliding = true;

                    }

                }

                velocity = invertedChangeOfBasisMatrix.multiplyByVector(velocity);

            }

            if (colliding) {

                for (float collisionTime : collisionTimes) {

                    if (Math.abs(collisionTime) < Math.abs(finalCollisionTime)) {

                        finalCollisionTime = collisionTime;

                        int index = -1;

                        for (int count = 0; count < collisionTimes.length; count++) {

                            if (collisionTime == collisionTimes[count]) {

                                index = count;
                                break;

                            }

                        }

                        finalCollisionFace = collisionFaces[index];

                    }

                }

                if (Math.abs(finalCollisionTime) <= 0.001f) finalCollisionTime = 0;

                if (finalCollisionTime != 0)
                    movedSpace = VectorHelper.multiplyVectorByFloat(velocity, finalCollisionTime);

                else {

                    for (Face face : allFaces) {

                        if (finalCollisionFace == face) {

                            collidingObject = objList.get(allFaces.indexOf(face));
                            break;

                        }

                    }

                    if (collidingObject != null) {

                        MovementComponent movementComponentB = (MovementComponent) collidingObject.getComponent("movementComponent");
                        CollideComponent collideComponentB = (CollideComponent) collidingObject.getComponent("collideComponent");
                        Controller controllerA = (Controller) objA.getComponent("controller");
                        Controller controllerB = (Controller) collidingObject.getComponent("controller");

                        /*if(collideComponentA.destroyable) {

                            movedSpace = VectorHelper.multiplyVectorByFloat(velocity, finalCollisionTime);

                            if(collideComponentB.destroyable) {

                                movedSpace = VectorHelper.multiplyVectorByFloat(velocity, finalCollisionTime);

                            }

                        } else {*/

                        if (collideComponentB.destroyable) {

                            movedSpace = VectorHelper.multiplyVectorByFloat(velocity, finalCollisionTime);

                        } else {

                            Vector3f vertexA = new Vector3f(allVertices.get((int) finalCollisionFace.vertexIndices.x));
                            Vector3f vertexB = new Vector3f(allVertices.get((int) finalCollisionFace.vertexIndices.y));
                            Vector3f vertexC = new Vector3f(allVertices.get((int) finalCollisionFace.vertexIndices.z));

                            Vector3f vertexBToCalculate = VectorHelper.subtractVectors(vertexB, vertexA);
                            Vector3f vertexCToCalculate = VectorHelper.subtractVectors(vertexC, vertexA);

                            int index = -1;

                            for (int count = 0; count < collisionFaces.length; count++) {

                                if (collisionFaces[count] == finalCollisionFace) {

                                    index = count;
                                    break;

                                }

                            }

                            Vector3f middle = middles.get(index);
                            middle = VectorHelper.subtractVectors(middle, vertexA);

                            Vector3f radius = radii.get(index);

                            Matrix3d changeOfBasisMatrix = new Matrix3d(new Vector3f(1 / radius.x, 0, 0), new Vector3f(0, 1 / radius.y, 0), new Vector3f(0, 0, 1 / radius.z));
                            //Matrix3d invertedChangeOfBasisMatrix = new Matrix3d(new Vector3f(radius.x, 0, 0), new Vector3f(0, radius.y, 0), new Vector3f(0, 0, radius.z));

                            vertexBToCalculate = changeOfBasisMatrix.multiplyByVector(vertexBToCalculate);
                            vertexCToCalculate = changeOfBasisMatrix.multiplyByVector(vertexCToCalculate);
                            middle = changeOfBasisMatrix.multiplyByVector(middle);
                            velocity = changeOfBasisMatrix.multiplyByVector(velocity);

                            Vector3f temporaryVelocity = VectorHelper.sumVectors(new Vector3f[]{velocity, middle});

                            Vector3f intersectionPoint = VectorHelper.sumVectors(new Vector3f[]{middle,
                                    VectorHelper.multiplyVectorByFloat(allNormals.get((int) finalCollisionFace.normalIndices.x), -1)});

                            float alpha;
                            float beta;
                            float gamma;

                            Matrix3d xAxisRotationMatrix;
                            Matrix3d yAxisRotationMatrix;
                            Matrix3d zAxisRotationMatrix;

                            Vector3f alphaIndicator = new Vector3f(vertexBToCalculate.x, vertexBToCalculate.y, 0);

                            alpha = VectorHelper.getAngle(alphaIndicator, vertexBToCalculate);

                            yAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(alpha), 0, (float) Math.sin(alpha)),
                                    new Vector3f(0, 1, 0),
                                    new Vector3f(-(float) Math.sin(alpha), 0, (float) Math.cos(alpha)));

                            vertexBToCalculate = yAxisRotationMatrix.multiplyByVector(vertexBToCalculate);
                            vertexCToCalculate = yAxisRotationMatrix.multiplyByVector(vertexCToCalculate);
                            middle = yAxisRotationMatrix.multiplyByVector(middle);
                            temporaryVelocity = yAxisRotationMatrix.multiplyByVector(temporaryVelocity);
                            intersectionPoint = yAxisRotationMatrix.multiplyByVector(intersectionPoint);

                            Vector3f betaIndicator = new Vector3f(1, 0, 0);

                            beta = VectorHelper.getAngle(betaIndicator, vertexBToCalculate);

                            zAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(beta), -(float) Math.sin(beta), 0),
                                    new Vector3f((float) Math.sin(beta), (float) Math.cos(beta), 0),
                                    new Vector3f(0, 0, 1));

                            vertexCToCalculate = zAxisRotationMatrix.multiplyByVector(vertexCToCalculate);
                            middle = zAxisRotationMatrix.multiplyByVector(middle);
                            temporaryVelocity = zAxisRotationMatrix.multiplyByVector(temporaryVelocity);
                            intersectionPoint = zAxisRotationMatrix.multiplyByVector(intersectionPoint);

                            Vector3f gammaIndicator = new Vector3f(vertexCToCalculate.x, vertexCToCalculate.y, 0);

                            gamma = VectorHelper.getAngle(gammaIndicator, vertexCToCalculate);

                            xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                                    new Vector3f(0, (float) Math.cos(gamma), -(float) Math.sin(gamma)),
                                    new Vector3f(0, (float) Math.sin(gamma), (float) Math.cos(gamma)));

                            middle = xAxisRotationMatrix.multiplyByVector(middle);
                            temporaryVelocity = xAxisRotationMatrix.multiplyByVector(temporaryVelocity);
                            intersectionPoint = xAxisRotationMatrix.multiplyByVector(intersectionPoint);

                            Vector3f newVelocity = new Vector3f(VectorHelper.subtractVectors(temporaryVelocity, intersectionPoint).x,
                                    VectorHelper.subtractVectors(temporaryVelocity, intersectionPoint).y,
                                    0);

                            Vector3f temporaryNewVelocity = VectorHelper.sumVectors(new Vector3f[]{newVelocity, middle});

                            Matrix3d invertedXAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                                    new Vector3f(0, (float) Math.cos(-gamma), -(float) Math.sin(-gamma)),
                                    new Vector3f(0, (float) Math.sin(-gamma), (float) Math.cos(-gamma)));

                            temporaryNewVelocity = invertedXAxisRotationMatrix.multiplyByVector(temporaryNewVelocity);
                            middle = invertedXAxisRotationMatrix.multiplyByVector(middle);

                            Matrix3d invertedZAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(-beta), -(float) Math.sin(-beta), 0),
                                    new Vector3f((float) Math.sin(-beta), (float) Math.cos(-beta), 0),
                                    new Vector3f(0, 0, 1));

                            temporaryNewVelocity = invertedZAxisRotationMatrix.multiplyByVector(temporaryNewVelocity);
                            middle = invertedZAxisRotationMatrix.multiplyByVector(middle);

                            Matrix3d invertedYAxisRotationMatrix = new Matrix3d(new Vector3f((float) Math.cos(-alpha), 0, (float) Math.sin(-alpha)),
                                    new Vector3f(0, 1, 0),
                                    new Vector3f(-(float) Math.sin(-alpha), 0, (float) Math.cos(-alpha)));

                            temporaryNewVelocity = invertedYAxisRotationMatrix.multiplyByVector(temporaryNewVelocity);
                            middle = invertedYAxisRotationMatrix.multiplyByVector(middle);

                            newVelocity = VectorHelper.subtractVectors(temporaryNewVelocity, middle);

                            if (controllerA == null) {

                                movedSpace = newVelocity;

                            } else {

                                movedSpace = newVelocity;

                            }

                                /*if(movementComponentB != null) {

                                    if(controllerB == null) {

                                        movedSpace = VectorHelper.multiplyVectorByFloat(velocity, finalCollisionTime);

                                    } else {

                                        movedSpace = VectorHelper.multiplyVectorByFloat(velocity, finalCollisionTime);

                                    }

                                }*/

                        }

                        //}

                    }

                }

            } else movedSpace = velocity;

        } else movedSpace = velocity;

        /*float deltaTime = TimeHelper.deltaTime;

        Vector3f speed = ForceController.getSpeedInReverse(movedSpace, deltaTime);
        Vector3f acceleration = ForceController.getAccelerationInReverse(speed, deltaTime);
        Vector3f forceDirection = ForceController.getForceDirectionInReverse(acceleration, renderComponentA.model.mass);

        String forceIdentifier = "inertiaForce" + movementComponentA.forcePoints.get("middle").forceCount;
        movementComponentA.forcePoints.get("middle").forces.put(forceIdentifier, new Force(forceDirection));
        movementComponentA.forcePoints.get("middle").forces.get(forceIdentifier).enabled = true;
        movementComponentA.forcePoints.get("middle").forceCount++;*/

        movementComponentA.movedSpace = movedSpace;

    }

}
