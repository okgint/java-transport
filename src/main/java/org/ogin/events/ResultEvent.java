package org.ogin.events;

import org.ogin.messages.Message;
import org.ogin.messages.RequestMessage;
import org.ogin.messages.responses.ResultMessage;

/**
 * @author ogin
 */
public class ResultEvent<M extends Message> extends AbstractResponseEvent<ResultMessage> {
    public ResultEvent(RequestMessage request, ResultMessage response) {
        super(request, response);
    }

    public Type getType() {
        return Type.RESULT;
    }

    public Object getResult() {
        return response.getResult();
    }

    @Override
    public String toString() {
        return getClass().getName() + " {result=" + response.getResult() + "}";
    }
}
