package mEngine.physics;

import mEngine.core.ObjectController;
import mEngine.graphics.renderable.Face;
import mEngine.interactive.gameObjects.GameObject;
import mEngine.interactive.gameObjects.GameObjectMovable;
import mEngine.util.vectorHelper.Matrix3d;
import mEngine.util.vectorHelper.VectorHelper;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collider {

       public static boolean areAABBsColliding(GameObjectMovable a, GameObject b) {

           boolean colliding;

           Vector3f posA = a.position;
           Vector3f posB = b.position;

           Vector3f sizeA = a.model.getSize();
           Vector3f sizeB = b.model.getSize();

           colliding = posA.x < posB.x + sizeB.x
                   && posA.x + sizeA.x > posB.x
                   && posA.y < posB.y + sizeB.y
                   && posA.y + sizeA.y > posB.y
                   && posA.z < posB.z + sizeB.z
                   && posA.z + sizeA.z > posB.z;

           return colliding;

       }

        public static boolean isCollidingWithSomething(GameObjectMovable objA) {

            boolean colliding = false;



            return colliding;

        }

        public static Vector3f getMovedSpace(Vector3f velocity, GameObjectMovable objA) {

            Vector3f movedSpace = new Vector3f();
            List<Face> allFaces = new ArrayList<Face>();
            List<Vector3f> allVertices = new ArrayList<Vector3f>();
            List<Vector3f> allNormals = new ArrayList<Vector3f>();
            boolean maybeColliding = false;
            boolean colliding = true;
            float[] collisionTimes = new float[objA.model.faces.size()];
            float finalCollisionTime = 2f;
            Face[] collisionFaces = new Face[objA.model.faces.size()];
            Face finalCollisionFace;

            for(GameObject objB : ObjectController.gameObjects) {

                if(objB.model != null && objB.collidable) {

                    if(objB != objA) {

                        if(areAABBsColliding(objA, objB)) {

                            for(Face face : objB.model.faces) {

                                allFaces.add(new Face(face));

                                Face newFace = allFaces.get(allFaces.size() - 1);

                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{objB.model.vertices.get((int) face.vertexIndices.x), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{objB.model.vertices.get((int) face.vertexIndices.y), objB.position})));
                                allVertices.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{objB.model.vertices.get((int) face.vertexIndices.z), objB.position})));

                                allNormals.add(new Vector3f(objB.model.normals.get((int)face.normalIndices.x)));
                                allNormals.add(new Vector3f(objB.model.normals.get((int)face.normalIndices.y)));
                                allNormals.add(new Vector3f(objB.model.normals.get((int)face.normalIndices.z)));

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

                //while(colliding) {

                    colliding = false;

                    for(Face faceA : objA.model.faces) {

                        collisionTimes[objA.model.faces.indexOf(faceA)] = 2f;
                        Vector3f maxVertexPos;
                        Vector3f minVertexPos;
                        Vector3f middle;
                        Vector3f radius;
                        List<Vector3f> verticesA = new ArrayList<Vector3f>();
                        List<Vector3f> verticesToCalculate = new ArrayList<Vector3f>();

                        verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{objA.model.vertices.get((int) faceA.vertexIndices.x), objA.position})));
                        verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{objA.model.vertices.get((int) faceA.vertexIndices.y), objA.position})));
                        verticesA.add(new Vector3f(VectorHelper.sumVectors(new Vector3f[]{objA.model.vertices.get((int) faceA.vertexIndices.z), objA.position})));

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
                        Matrix3d invertedChangeOfBasisMatrix = new Matrix3d(new Vector3f(radius.x, 0, 0), new Vector3f(0, radius.y, 0), new Vector3f(0, 0, radius.z));

                        middle = changeOfBasisMatrix.multiplyByVector(middle);
                        Vector3f originalMiddle = new Vector3f(middle);
                        velocity = changeOfBasisMatrix.multiplyByVector(velocity);

                        for(float count = 0; count <= 1; count += 0.001f) {

                            if(collisionTimes[objA.model.faces.indexOf(faceA)] <= 1) break;

                            middle = originalMiddle;

                            middle = VectorHelper.sumVectors(new Vector3f[] {middle, VectorHelper.multiplyVectors(new Vector3f[] {velocity, new Vector3f(count, count, count)})});

                            for(Face faceB : allFaces) {

                                Vector3f normal = allNormals.get((int)faceB.normalIndices.x);
                                Vector3f vertex = allVertices.get((int)faceB.vertexIndices.x);

                                //Special thanks to Mike Ganshorn for this piece of code
                                float difference = Math.abs(VectorHelper.getScalarProduct(normal, middle) + VectorHelper.getScalarProduct(normal, vertex));

                                if(difference <= 1) {

                                    colliding = true;
                                    collisionTimes[objA.model.faces.indexOf(faceA)] = count;
                                    collisionFaces[objA.model.faces.indexOf(faceA)] = faceB;

                                }

                            }

                        }

                        //if(objA == ObjectController.getGameObject(0)) System.out.println(colliding);

                        velocity = invertedChangeOfBasisMatrix.multiplyByVector(velocity);

                    }

                    System.out.println(colliding);

                    if(!colliding) {

                        movedSpace = velocity;
                        //break;

                    }

                    for(float collisionTime : collisionTimes) {

                        if(collisionTime < finalCollisionTime) {

                            finalCollisionTime = collisionTime;
                            //finalCollisionFace = collisionFaces[Arrays.asList(collisionTimes).indexOf(collisionTime)];

                        }

                    }

                    System.out.println(finalCollisionTime);

                    if(finalCollisionTime != 0) movedSpace = VectorHelper.multiplyVectors(new Vector3f[] {velocity, new Vector3f(finalCollisionTime, finalCollisionTime, finalCollisionTime)});

                    else {

                        movedSpace = new Vector3f();

                    }

                //}

            }

            else movedSpace = velocity;

            return  movedSpace;

        }

}
