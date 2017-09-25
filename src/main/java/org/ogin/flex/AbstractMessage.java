package org.ogin.flex;

import org.ogin.util.StringUtil;
import org.ogin.util.UUIDUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ogin
 */
public abstract class AbstractMessage implements Message {
    private Object body = null;
    private Object clientId = null;
    private String destination = null;
    private Map<String, Object> headers = null;
    private String messageId = null;
    private long timestamp = 0L;
    private long timeToLive = 0L;

    public AbstractMessage() {
        super();
    }

    public AbstractMessage(Message request) {
        this(request, false);
    }

    public AbstractMessage(Message request, boolean keepClientId) {
        super();
        this.messageId = UUIDUtil.randomUUID();
        this.timestamp = System.currentTimeMillis();
        this.clientId = (keepClientId && request.getClientId() != null ? request.getClientId() : UUIDUtil.randomUUID());
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Object getClientId() {
        return clientId;
    }

    public void setClientId(Object clientId) {
        this.clientId = clientId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Object getHeader(String name) {
        return (headers != null ? headers.get(name) : null);
    }

    public boolean headerExists(String name) {
        return (headers != null ? headers.containsKey(name) : false);
    }

    public void setHeader(String name, Object value) {
        if (headers == null)
            headers = new HashMap<String, Object>();
        headers.put(name, value);
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    protected void toString(StringBuilder sb, String indent, String bodyAlternative) {
        sb.append('\n').append(indent).append("  destination = ").append(destination);

        if (headers != null && headers.containsKey(REMOTE_CREDENTIALS_HEADER)) {
            Map<String, Object> headersCopy = new HashMap<String, Object>(headers);
            headersCopy.put(REMOTE_CREDENTIALS_HEADER, HIDDEN_CREDENTIALS);
            sb.append('\n').append(indent).append("  headers = ").append(headersCopy);
        }
        else
            sb.append('\n').append(indent).append("  headers = ").append(headers);

        sb.append('\n').append(indent).append("  messageId = ").append(messageId);
        sb.append('\n').append(indent).append("  timestamp = ").append(timestamp);
        sb.append('\n').append(indent).append("  clientId = ").append(clientId);
        sb.append('\n').append(indent).append("  timeToLive = ").append(timeToLive);
        sb.append('\n').append(indent).append("  body = ").append(printBody(body, bodyAlternative));
    }

    private static String printBody(Object body, String bodyAlternative) {

        body = (bodyAlternative != null ? bodyAlternative : body);
        if (body == null)
            return null;

        if (body.getClass().isArray() || body instanceof Collection<?> || body instanceof Map<?, ?>)
            return StringUtil.toString(body, 100); // limit to first 100 elements.

        return body.toString();
    }
}
