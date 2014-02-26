package mEngine.physics;

import mEngine.core.ObjectController;
import mEngine.graphics.renderable.Face;
import mEngine.interactive.components.CollideComponent;
import mEngine.interactive.components.MovementComponent;
import mEngine.interactive.components.RenderComponent;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.util.vectorHelper.Matrix3d;
import mEngine.util.vectorHelper.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Collider {

       public static boolean areAABBsColliding(GameObject objA, GameObject objB) {

           boolean colliding;

           RenderComponent renderComponentA = (RenderComponent)objA.getComponent("renderComponent");
           CollideComponent collideComponentA = (CollideComponent)objA.getComponent("collideComponent");

           RenderComponent renderComponentB = (RenderComponent)objB.getComponent("renderComponent");
           CollideComponent collideComponentB = (CollideComponent)objB.getComponent("collideComponent");

           if(renderComponentA != null && renderComponentB != null && collideComponentA != null && collideComponentB != null) {

               Vector3f posA = objA.position;
               Vector3f posB = objB.position;

               Vector3f sizeA = renderComponentA.model.getSize();
               Vector3f sizeB = renderComponentB.model.getSize();

               colliding = posA.x < posB.x + sizeB.x
                       && posA.x + sizeA.x > posB.x
                       && posA.y < posB.y + sizeB.y
                       && posA.y + sizeA.y > posB.y
                       && posA.z < posB.z + sizeB.z
                       && posA.z + sizeA.z > posB.z;

           }

           else colliding = false;

           return colliding;

       }

        public static boolean isCollidingWithSomething(GameObject objA) {

            boolean colliding = false;
            List<Face> allFaces = new ArrayList<Face>();
            List<Vector3f> allVertices = new ArrayList<Vector3f>();
            List<Vector3f> allNormals = new ArrayList<Vector3f>();
            boolean maybeColliding = false;
            RenderComponent renderComponentA = (RenderComponent)objA.getComponent("renderComponent");
            CollideComponent collideComponentA = (CollideComponent)objA.getComponent("collideComponent");

            for(GameObject objB : ObjectController.gameObjects) {

                if(objA != objB) {

                    RenderComponent renderComponentB = (RenderComponent)objB.getComponent("renderComponent");
                    CollideComponent collideComponentB = (CollideComponent)objB.getComponent("collideComponent");

                    if(renderComponentB != null && collideComponentB != null && renderComponentA != null && collideComponentA != null) {

                        if(areAABBsColliding(objA, objB)) {

                            for(Face face : renderComponentB.model.faces) {

                                allFaces.add(new Face(face));

                                Face newFace = allFaces.get(allFaces.size() - 1);

                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.x), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.y), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.z), objB.position})));

                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int)face.normalIndices.x)));
                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int)face.normalIndices.y)));
                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int)face.normalIndices.z)));

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

            if(maybeColliding) {

                for(Face faceA : renderComponentA.model.faces) {

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
                    verticesToCalculate.add(new Vector3f(VectorHelper.subtractVectors(verticesA.get(1), verticesA.get(0))));
                    verticesToCalculate.add(new Vector3f(VectorHelper.subtractVectors(verticesA.get(2), verticesA.get(0))));

                    maxVertexPos = new Vector3f();
                    minVertexPos = new Vector3f();

                    for(Vector3f vertex : verticesToCalculate) {

                        if(vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
                        else if(vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

                        if(vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
                        else if(vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

                        if(vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
                        else if(vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

                    }

                    maxVertexPos = VectorHelper.sumVectors(new Vector3f[] {maxVertexPos, verticesA.get(0)});
                    minVertexPos = VectorHelper.sumVectors(new Vector3f[] {minVertexPos, verticesA.get(0)});

                    middle = VectorHelper.divideVectors(VectorHelper.sumVectors(new Vector3f[] {minVertexPos, maxVertexPos}), new Vector3f(2f, 2f, 2f));

                    radius = VectorHelper.subtractVectors(maxVertexPos, middle);

                    Matrix3d changeOfBasisMatrix = new Matrix3d(new Vector3f(1 / radius.x, 0, 0), new Vector3f(0, 1 / radius.y, 0), new Vector3f(0, 0, 1 / radius.z));

                    middle = changeOfBasisMatrix.multiplyByVector(middle);

                    for(Face faceB : allFaces) {

                        Vector3f normal = allNormals.get((int)faceB.normalIndices.x);
                        Vector3f vertex = allVertices.get((int)faceB.vertexIndices.x);

                        //Special thanks to Mike Ganshorn for this piece of code
                        float difference = Math.abs(VectorHelper.getScalarProduct(normal, middle) + VectorHelper.getScalarProduct(normal, vertex));

                        if(difference <= 1) {

                            colliding = true;

                        }

                    }

                }

            }

            return colliding;

        }

        public static Vector3f getMovedSpace(GameObject objA) {

            RenderComponent renderComponentA = (RenderComponent)objA.getComponent("renderComponent");
            CollideComponent collideComponentA = (CollideComponent)objA.getComponent("collideComponent");
            MovementComponent movementComponentA = (MovementComponent)objA.getComponent("movementComponent");
            Vector3f velocity = new Vector3f(movementComponentA.movedSpace);
            Vector3f movedSpace;
            List<Face> allFaces = new ArrayList<Face>();
            List<Vector3f> allVertices = new ArrayList<Vector3f>();
            List<Vector3f> allNormals = new ArrayList<Vector3f>();
            boolean maybeColliding = false;
            boolean colliding = false;

            float[] collisionTimes = null;
            Face[] collisionFaces = null;

            if(renderComponentA != null) {

                collisionTimes = new float[renderComponentA.model.faces.size()];
                collisionFaces = new Face[renderComponentA.model.faces.size()];

            }

            float finalCollisionTime = 2f;
            Face finalCollisionFace = null;
            List<GameObject> objList = new ArrayList<GameObject>();
            GameObject collidingObject = null;

            for(GameObject objB : ObjectController.gameObjects) {

                if(objA != objB) {

                    RenderComponent renderComponentB = (RenderComponent)objB.getComponent("renderComponent");
                    CollideComponent collideComponentB = (CollideComponent)objB.getComponent("collideComponent");

                    if(renderComponentA != null && renderComponentB != null && collideComponentB != null) {

                        if(areAABBsColliding(objA, objB)) {

                            for(Face face : renderComponentB.model.faces) {

                                allFaces.add(new Face(face));
                                objList.add(objB);

                                Face newFace = allFaces.get(allFaces.size() - 1);

                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.x), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.y), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{renderComponentB.model.vertices.get((int) face.vertexIndices.z), objB.position})));

                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int)face.normalIndices.x)));
                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int)face.normalIndices.y)));
                                allNormals.add(new Vector3f(renderComponentB.model.normals.get((int)face.normalIndices.z)));

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

            if(maybeColliding) {

                List<Vector3f> middles = new ArrayList<Vector3f>();
                List<Vector3f> radii = new Vector<Vector3f>();

                for(Face faceA : renderComponentA.model.faces) {

                    collisionTimes[renderComponentA.model.faces.indexOf(faceA)] = 2;
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

                    for(Vector3f vertex : verticesToCalculate) {

                        if(vertex.x > maxVertexPos.x) maxVertexPos.x = vertex.x;
                        else if(vertex.x < minVertexPos.x) minVertexPos.x = vertex.x;

                        if(vertex.y > maxVertexPos.y) maxVertexPos.y = vertex.y;
                        else if(vertex.y < minVertexPos.y) minVertexPos.y = vertex.y;

                        if(vertex.z > maxVertexPos.z) maxVertexPos.z = vertex.z;
                        else if(vertex.z < minVertexPos.z) minVertexPos.z = vertex.z;

                    }

                    maxVertexPos = VectorHelper.sumVectors(new Vector3f[] {maxVertexPos, verticesA.get(0)});
                    minVertexPos = VectorHelper.sumVectors(new Vector3f[] {minVertexPos, verticesA.get(0)});

                    middles.add(VectorHelper.divideVectors(VectorHelper.sumVectors(new Vector3f[]{minVertexPos, maxVertexPos}), new Vector3f(2f, 2f, 2f)));
                    Vector3f middle = middles.get(middles.size() - 1);

                    radii.add(VectorHelper.subtractVectors(maxVertexPos, middle));
                    Vector3f radius = radii.get(radii.size() - 1);

                    Matrix3d changeOfBasisMatrix = new Matrix3d(new Vector3f(1 / radius.x, 0, 0), new Vector3f(0, 1 / radius.y, 0), new Vector3f(0, 0, 1 / radius.z));
                    Matrix3d invertedChangeOfBasisMatrix = new Matrix3d(new Vector3f(radius.x, 0, 0), new Vector3f(0, radius.y, 0), new Vector3f(0, 0, radius.z));

                    middle = changeOfBasisMatrix.multiplyByVector(middle);
                    velocity = changeOfBasisMatrix.multiplyByVector(velocity);

                    for(Face faceB : allFaces) {

                        Vector3f normal = allNormals.get((int)faceB.normalIndices.x);
                        Vector3f vertex = allVertices.get((int)faceB.vertexIndices.x);

                        //Special thanks to Mike Ganshorn for this piece of code
                        float difference = Math.abs(VectorHelper.getScalarProduct(normal, middle) + VectorHelper.getScalarProduct(normal, vertex)) - 1;

                        float collisionTime = difference / VectorHelper.getAbs(velocity);

                        if(collisionTime <= 1 && collisionTime < collisionTimes[renderComponentA.model.faces.indexOf(faceA)]) {

                            colliding = true;
                            collisionTimes[renderComponentA.model.faces.indexOf(faceA)] = collisionTime;
                            collisionFaces[renderComponentA.model.faces.indexOf(faceA)] = faceB;

                        }

                    }

                    velocity = invertedChangeOfBasisMatrix.multiplyByVector(velocity);

                }

                System.out.println(colliding);

                if(colliding) {

                    for(float collisionTime : collisionTimes) {

                        if(collisionTime < finalCollisionTime) {

                            finalCollisionTime = collisionTime;

                            int index = -1;

                            for(int count = 0; count < collisionTimes.length; count ++) {

                                if(collisionTime == collisionTimes[count]) index = count;

                            }

                            finalCollisionFace = collisionFaces[index];

                        }

                    }

                    //System.out.println(finalCollisionTime);

                    if(finalCollisionTime != 0) movedSpace = VectorHelper.multiplyVectors(new Vector3f[]
                            {velocity, new Vector3f(finalCollisionTime, finalCollisionTime, finalCollisionTime)});

                    else {

                        for(Face face : allFaces) {

                            if(finalCollisionFace == face) {

                                collidingObject = objList.get(allFaces.indexOf(face));
                                break;

                            }

                        }

                        if(collidingObject != null) {

                            MovementComponent movementComponentB = (MovementComponent)collidingObject.getComponent("movementComponent");
                            CollideComponent collideComponentB = (CollideComponent)collidingObject.getComponent("collideComponent");

                            if(movementComponentB == null) {

                                if(collideComponentB.destroyable) {



                                } else {

                                    Vector3f vertexA = new Vector3f(allVertices.get((int)finalCollisionFace.vertexIndices.x));
                                    Vector3f vertexB = new Vector3f(allVertices.get((int)finalCollisionFace.vertexIndices.y));
                                    Vector3f vertexC = new Vector3f(allVertices.get((int)finalCollisionFace.vertexIndices.z));

                                    Vector3f vertexBToCalculate = VectorHelper.subtractVectors(vertexB, vertexA);
                                    Vector3f vertexCToCalculate = VectorHelper.subtractVectors(vertexC, vertexA);

                                    int index = -1;

                                    for(int count = 0; count < collisionFaces.length; count ++) {

                                        if(collisionFaces[count] == finalCollisionFace) {

                                            index = count;
                                            break;

                                        }

                                    }

                                    Vector3f radius = radii.get(index);

                                    Vector3f middle = middles.get(index);
                                    middle = VectorHelper.subtractVectors(middle, vertexA);


                                    Vector3f temporaryVelocity = VectorHelper.sumVectors(new Vector3f[] {velocity, middle});

                                    Vector3f invertedNormal = VectorHelper.multiplyVectors(new Vector3f[]
                                            {allNormals.get((int)finalCollisionFace.normalIndices.x), new Vector3f(-1, -1, -1)});

                                    Vector3f intersectionPoint = VectorHelper.sumVectors(new Vector3f[] {middle, invertedNormal});
                                    intersectionPoint = VectorHelper.subtractVectors(intersectionPoint, vertexA);

                                    float alpha;
                                    float beta;
                                    float gamma;

                                    Matrix3d xAxisRotationMatrix;

                                    Matrix3d yAxisRotationMatrix;

                                    Matrix3d zAxisRotationMatrix;

                                    Vector3f alphaIndicator = new Vector3f(vertexBToCalculate.x, vertexBToCalculate.y, 0);

                                    alpha = (float)Math.acos(VectorHelper.getScalarProduct(alphaIndicator, vertexBToCalculate)
                                            / (VectorHelper.getAbs(alphaIndicator) * VectorHelper.getAbs(vertexBToCalculate)));

                                    yAxisRotationMatrix = new Matrix3d(new Vector3f((float)Math.cos(alpha), 0, (float)Math.sin(alpha)),
                                            new Vector3f(0, 1, 0),
                                            new Vector3f(-(float)Math.sin(alpha), 0, (float)Math.cos(alpha)));

                                    vertexBToCalculate = yAxisRotationMatrix.multiplyByVector(vertexBToCalculate);
                                    vertexCToCalculate = yAxisRotationMatrix.multiplyByVector(vertexCToCalculate);
                                    middle = yAxisRotationMatrix.multiplyByVector(middle);
                                    temporaryVelocity = yAxisRotationMatrix.multiplyByVector(temporaryVelocity);
                                    intersectionPoint = yAxisRotationMatrix.multiplyByVector(intersectionPoint);

                                    Vector3f betaIndicator = new Vector3f(1, 0, 0);

                                    beta = (float)Math.acos(VectorHelper.getScalarProduct(betaIndicator, vertexBToCalculate)
                                            / (VectorHelper.getAbs(betaIndicator) * VectorHelper.getAbs(vertexBToCalculate)));

                                    zAxisRotationMatrix = new Matrix3d(new Vector3f((float)Math.cos(beta), -(float)Math.sin(beta), 0),
                                            new Vector3f((float)Math.sin(beta), (float)Math.cos(beta), 0),
                                            new Vector3f(0, 0, 1));

                                    vertexBToCalculate = zAxisRotationMatrix.multiplyByVector(vertexBToCalculate);
                                    vertexCToCalculate = zAxisRotationMatrix.multiplyByVector(vertexCToCalculate);
                                    middle = zAxisRotationMatrix.multiplyByVector(middle);
                                    temporaryVelocity = zAxisRotationMatrix.multiplyByVector(temporaryVelocity);
                                    intersectionPoint = zAxisRotationMatrix.multiplyByVector(intersectionPoint);

                                    Vector3f gammaIndicator = new Vector3f(vertexCToCalculate.x, vertexCToCalculate.y, 0);

                                    gamma = (float)Math.acos(VectorHelper.getScalarProduct(gammaIndicator, vertexCToCalculate)
                                            / (VectorHelper.getAbs(gammaIndicator) * VectorHelper.getAbs(vertexCToCalculate)));

                                    xAxisRotationMatrix = new Matrix3d(new Vector3f(1, 0, 0),
                                            new Vector3f(0, (float)Math.cos(gamma), -(float)Math.sin(gamma)),
                                            new Vector3f(0, (float)Math.sin(gamma), (float)Math.cos(gamma)));

                                    vertexCToCalculate = xAxisRotationMatrix.multiplyByVector(vertexCToCalculate);
                                    middle = xAxisRotationMatrix.multiplyByVector(middle);
                                    temporaryVelocity = xAxisRotationMatrix.multiplyByVector(temporaryVelocity);
                                    intersectionPoint = xAxisRotationMatrix.multiplyByVector(intersectionPoint);



                                }

                            } else {

                                if(collideComponentB.destroyable) {



                                } else {



                                }

                            }

                        }

                        movedSpace = velocity;

                    }

                }

                else movedSpace = velocity;

            }

            else movedSpace = velocity;

            return  movedSpace;

        }

}
