package org.ogin.flex;

import java.util.HashMap;

/**
 * @author ogin
 */
public class AsyncMessage extends AbstractMessage {
    public static final String SUBTOPIC_HEADER = "DSSubtopic";
    public static final String DESTINATION_CLIENT_ID_HEADER = "DSDstClientId";

    private String correlationId;

    public AsyncMessage() {
        super();

        setHeaders(new HashMap<String, Object>());
    }

    public AsyncMessage(Message request) {
        this(request, false);
    }

    public AsyncMessage(Message request, boolean keepClientId) {
        super(request, keepClientId);

        setHeaders(new HashMap<String, Object>());
        this.correlationId = request.getMessageId();
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public AsyncMessage clone() {
        AsyncMessage msg = new AsyncMessage();
        msg.setBody(getBody());
        msg.setClientId(getClientId());
        msg.setCorrelationId(getCorrelationId());
        msg.setDestination(getDestination());
        msg.setMessageId(getMessageId());
        msg.setHeaders(new HashMap<String, Object>(getHeaders()));
        msg.setTimestamp(getTimestamp());
        msg.setTimeToLive(getTimeToLive());
        return msg;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(String indent) {
        StringBuilder sb = new StringBuilder(512);
        sb.append(getClass().getName()).append(" {");
        toString(sb, indent, null);
        sb.append('\n').append(indent).append('}');
        return sb.toString();
    }

    @Override
    protected void toString(StringBuilder sb, String indent, String bodyMessage) {
        sb.append('\n').append(indent).append("  correlationId = ").append(correlationId);
        super.toString(sb, indent, bodyMessage);
    }
}
