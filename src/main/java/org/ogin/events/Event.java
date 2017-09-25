package org.ogin.events;

/**
 * @author ogin
 */
public interface Event {
    public static enum Type {
        RESULT,
        FAULT,
        FAILURE,
        TIMEOUT,
        CANCELLED,

        TOPIC
    }

    /**
     * Type of the event
     * @return type of the event
     */
    Type getType();
}
