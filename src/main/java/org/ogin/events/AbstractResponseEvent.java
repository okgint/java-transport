package org.ogin.events;

import org.ogin.messages.Message;
import org.ogin.messages.RequestMessage;
import org.ogin.messages.ResponseMessage;

/**
 * @author ogin
 */
public abstract class AbstractResponseEvent<M extends ResponseMessage> implements IncomingMessageEvent<M> {
    protected final RequestMessage request;
    protected final M response;

    public AbstractResponseEvent(RequestMessage request, M response) {
        if (request == null || response == null)
            throw new NullPointerException("request and response cannot be null");

        this.request = request;
        this.response = response;
    }
    public RequestMessage getRequest() {
        return request;
    }

    public M getResponse() {
        return response;
    }

    public M getMessage() {
        return response;
    }
}
