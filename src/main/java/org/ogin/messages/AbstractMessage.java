package org.ogin.messages;

import org.ogin.util.UUIDUtil;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ogin
 */
public abstract class AbstractMessage implements Message {
    private String clientId = null;
    private long timestamp = 0L;
    private long timeToLive = 0L;
    private Map<String, Object> headers = new HashMap<String, Object>();
    private String id = UUIDUtil.randomUUID();


    public AbstractMessage() {
    }

    public AbstractMessage(String clientId) {
        this.clientId = clientId;
    }

    public AbstractMessage(
            String id,
            String clientId,
            long timestamp,
            long timeToLive,
            Map<String, Object> headers) {

        setId(id);
        this.clientId = clientId;
        this.timestamp = timestamp;
        this.timeToLive = timeToLive;
        this.headers = headers;
    }

    protected void copy(AbstractMessage message) {
        message.id = id;
        message.clientId = clientId;
        message.timestamp = timestamp;
        message.timeToLive = timeToLive;
        message.headers.putAll(headers);
    }

    public Message clone() throws CloneNotSupportedException {
        return copy();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isExpired() {
        return isExpired(System.currentTimeMillis());
    }

    public boolean isExpired(long currentTimeMillis) {
        return getRemainingTimeToLive(currentTimeMillis) < 0;
    }

    public Object getHeader(String name) {
        return headers.get(name);
    }

    public void setHeader(String name, Object value) {
        headers.put(name, value);
    }

    public boolean headerExists(String name) {
        return headers.containsKey(name);
    }

    public long getRemainingTimeToLive() {
        return getRemainingTimeToLive(System.currentTimeMillis());
    }

    public long getRemainingTimeToLive(long currentTimeMillis) {
        if (timestamp <= 0L || this.timeToLive <= 0L)
            throw new IllegalStateException("Unset timestamp/timeToLive: {timestamp=" + timestamp + ", timeToLive=" + timeToLive + "}");
        return timestamp + timeToLive - currentTimeMillis;
    }

    public boolean equals(Object obj) {
        return (obj instanceof AbstractMessage && id.equals(((AbstractMessage)obj).id));
    }

    public int hashCode() {
        return id.hashCode();
    }

    public String toString() {
        return toString(new StringBuilder(getClass().getName()).append(" {")).append("\n}").toString();
    }

    public StringBuilder toString(StringBuilder sb) {
        return sb
                .append("\n    id=").append(id)
                .append("\n    clientId=").append(clientId)
                .append("\n    timestamp=").append(timestamp)
                .append("\n    timeToLive=").append(timeToLive)
                .append("\n    headers=").append(headers);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        String id = in.readUTF();
        if (id == null)
            throw new RuntimeException("Message id cannot be null");
        this.id = id;

        clientId = in.readUTF();

        timestamp = in.readLong();
        timeToLive = in.readLong();

        Map<String, Object> headers = (Map<String, Object>)in.readObject();
        if (headers != null)
            this.headers = headers;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(id);
        if (clientId != null)
            out.writeUTF(clientId);
        else
            out.writeObject(null);

        out.writeLong(timestamp);
        out.writeLong(timeToLive);

        if (headers.size() > 0)
            out.writeObject(headers);
        else
            out.writeObject(null);
    }


}
