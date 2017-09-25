package org.ogin.events;

import org.ogin.messages.RequestMessage;

/**
 * @author ogin
 */
public class FailureEvent extends AbstractIssueEvent {
    private final Exception cause;

    public FailureEvent(RequestMessage request, Exception cause) {
        super(request);
        this.cause = cause;
    }

    public Type getType() {
        return Type.FAILURE;
    }

    public Exception getCause() {
        return cause;
    }

    @Override
    public String toString() {
        return getClass().getName() + " {cause=" + cause + "}";
    }
}
