/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.test;

import com.polygame.engine.util.math.NumberFormatHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 02.11.2014 21:51
 */
public class TestNumberFormatHelper {

    @Test
    public void testNumberFormat() {
        Locale.setDefault(Locale.US);
        Assert.assertEquals("1", NumberFormatHelper.cutDecimals(1.2345, 0));
        Assert.assertEquals("1.23", NumberFormatHelper.cutDecimals(1.2345, 2));
    }

}
