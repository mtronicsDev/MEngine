/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.core.events;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 13.11.2014 21:18
 */
public interface AdvancedEventHandler extends EventHandler {

    @Override
    public default void handle() {
    }

    /**
     * Allows for advanced handling of events.
     *
     * @param parameters The extra data for the event handler
     */
    public void handle(Object... parameters);
}
