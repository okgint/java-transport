package org.ogin.events;

import org.ogin.messages.RequestMessage;

/**
 * @author ogin
 */
public abstract class AbstractIssueEvent implements IssueEvent {
    private final RequestMessage request;

    public AbstractIssueEvent(RequestMessage request) {
        this.request = request;
    }

    public RequestMessage getRequest() {
        return request;
    }
}
