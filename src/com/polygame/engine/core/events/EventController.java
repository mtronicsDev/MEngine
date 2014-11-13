/*
 * Copyright (c) 2014 mgamelabs
 * To see our full license terms, please visit https://github.com/mgamelabs/mengine/blob/master/LICENSE.md
 * All rights reserved.
 */

package com.polygame.engine.core.events;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mtronics_dev (Maxi Schmeller)
 * @version 04.10.2014 00:02
 */
public class EventController {

    private static Map<EventHandler, String> eventMap = new HashMap<>();

    /**
     * Used to trigger an event.
     * This will execute the handle() method of all event handlers assigned to the event in question.
     *
     * @param event The event to trigger
     */
    public static void triggerEvent(String event) {
        eventMap.keySet().stream()
          .filter(handler -> eventMap.get(handler).equals(event))
          .forEach(EventHandler::handle);
    }

    /**
     * Used to trigger an advanced event.
     * This will execute the handle(Object... parameters) method of all event handlers assigned to the event in question.
     *
     * @param event The event to trigger
     */
    public static void triggerEvent(String event, Object... parameters) {
        eventMap.keySet().stream()
          .filter(handler -> eventMap.get(handler).equals(event))
          .filter(handler -> handler instanceof AdvancedEventHandler)
          .forEach(handler -> ((AdvancedEventHandler) handler).handle(parameters));
    }

    /**
     * Removes an event from the list and un-links all its handlers.
     *
     * @param event The event to remove
     */
    public static void removeEvent(String event) {
        eventMap.keySet().stream()
          .filter(handler -> eventMap.get(handler).equals(event))
          .forEach(eventMap::remove);
    }

    /**
     * Adds an event handler to a specific event.
     * All handlers linked to a event will be called when the event gets triggered.
     *
     * @param event   The event to link the handler to
     * @param handler The handler to link to the event
     */
    public static void addEventHandler(String event, EventHandler handler) {
        eventMap.put(handler, event);
    }

}
