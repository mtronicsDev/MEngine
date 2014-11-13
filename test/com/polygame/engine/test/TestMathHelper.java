/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.test;

import com.polygame.engine.util.math.MathHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 02.11.2014 21:15
 */
public class TestMathHelper {

    @Test
    public void testClamp() {
        Assert.assertEquals(6, MathHelper.clamp(6, 0, 7), 0);
        Assert.assertEquals(3, MathHelper.clamp(6, -4, 3), 0);
        Assert.assertEquals(7, MathHelper.clamp(6, 7, 9), 0);
    }

    @Test
    public void testClampMin() {
        Assert.assertEquals(9, MathHelper.clampMin(9, 7), 0);
        Assert.assertEquals(7, MathHelper.clampMin(3, 7), 0);
    }

    @Test
    public void testClampMax() {
        Assert.assertEquals(4, MathHelper.clampMax(4, 6), 0);
        Assert.assertEquals(2, MathHelper.clampMax(4, 2), 0);
    }

}
