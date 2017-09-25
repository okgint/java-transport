package org.ogin.events;

import org.ogin.messages.RequestMessage;

/**
 * @author ogin
 */
public class CancelledEvent extends AbstractIssueEvent {

    public CancelledEvent(RequestMessage request) {
        super(request);
    }

    public Type getType() {
        return Type.CANCELLED;
    }

    @Override
    public String toString() {
        return getClass().getName() + " {}";
    }
}
