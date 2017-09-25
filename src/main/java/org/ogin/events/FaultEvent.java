package org.ogin.events;

import org.ogin.messages.RequestMessage;
import org.ogin.messages.responses.FaultMessage;

import java.util.Map;

/**
 * @author ogin
 */
public class FaultEvent extends AbstractResponseEvent<FaultMessage> implements IssueEvent {
    public FaultEvent(RequestMessage request, FaultMessage response) {
        super(request, response);
    }

    public Type getType() {
        return Type.FAULT;
    }

    public FaultMessage.Code getCode() {
        return response.getCode();
    }

    public String getDescription() {
        return response.getDescription();
    }

    public String getDetails() {
        return response.getDetails();
    }

    public Object getCause() {
        return response.getCause();
    }

    public Map<String, Object> getExtended() {
        return response.getExtended();
    }

    public String getUnknownCode() {
        return response.getUnknownCode();
    }

    @Override
    public String toString() {
        return getClass().getName() + "{code=" + getCode() +
                ", description=" + getDescription() +
                ", details=" + getDetails() +
                ", cause=" + getCause() +
                ", extended=" + getExtended() +
                ", unknownCode=" + getUnknownCode() +
                "}";
    }
}
