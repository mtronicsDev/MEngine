/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.gameObjects.modules.particles.modules;

import com.polygame.engine.gameObjects.modules.renderable.Particle;

public abstract class ParticleComponent {

    public Particle parent;

    public void onCreation(Particle particle) {

        parent = particle;

    }

    public abstract void onUpdate();

}
