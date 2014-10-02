/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package mEngine.physics;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionConfiguration;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import mEngine.util.data.DataTypeHelper;
import mEngine.util.resources.PreferenceHelper;

import java.util.HashSet;
import java.util.Set;

public class PhysicsController {

    public static DynamicsWorld world; //The physics world

    private static BroadphaseInterface aabbInterface; //Checks if objects could collide
    private static CollisionConfiguration collisionConfiguration;
    private static CollisionDispatcher collisionDispatcher;
    private static ConstraintSolver constraintSolver;

    /**
     * Creates the physics world and sets up collisions
     */
    public static void initialize() {
        aabbInterface = new DbvtBroadphase();
        collisionConfiguration = new DefaultCollisionConfiguration();
        collisionDispatcher = new CollisionDispatcher(collisionConfiguration);
        constraintSolver = new SequentialImpulseConstraintSolver();
        world = new DiscreteDynamicsWorld(collisionDispatcher, aabbInterface, constraintSolver, collisionConfiguration);

        world.setGravity(DataTypeHelper.stringToVector3f(PreferenceHelper.getValue("gravity")));
    }

}
