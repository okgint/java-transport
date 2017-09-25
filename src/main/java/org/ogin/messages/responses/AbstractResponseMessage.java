package org.ogin.messages.responses;

import org.ogin.messages.AbstractMessage;
import org.ogin.messages.ResponseMessage;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author ogin
 */
public abstract class AbstractResponseMessage extends AbstractMessage implements ResponseMessage {
    private String correlationId;
    private boolean processed = false;
    private ResponseMessage next;

    public AbstractResponseMessage() {
    }

    public AbstractResponseMessage(String clientId, String correlationid) {
        super(clientId);
        this.correlationId = correlationid;
    }

    public AbstractResponseMessage(
            String id,
            String clientId,
            long timestamp,
            long timeToLive,
            Map<String, Object> headers,
            String correlationId) {

        super(id, clientId, timestamp, timeToLive, headers);

        this.correlationId = correlationId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed() {
        this.processed = true;
    }

    public void setNext(ResponseMessage next) {
        for (ResponseMessage n = next; n != null; n = n.getNext()) {
            if (n == this)
                throw new RuntimeException("Circular chaining to this: " + next);
        }
        this.next = next;
    }

    public ResponseMessage getNext() {
        return next;
    }

    public Iterator<ResponseMessage> iterator() {

        final ResponseMessage first = this;

        return new Iterator<ResponseMessage>() {

            private ResponseMessage current = first;

            public boolean hasNext() {
                return current != null;
            }

            public ResponseMessage next() {
                if (current == null)
                    throw new NoSuchElementException();
                ResponseMessage c = current;
                current = current.getNext();
                return c;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);

        this.correlationId = in.readUTF();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        if (correlationId != null)
            out.writeUTF(correlationId);
        else
            out.writeObject(null);
    }
    protected void copy(AbstractMessage message) {
        copy((AbstractResponseMessage)message, correlationId);
    }

    protected void copy(AbstractResponseMessage message, String correlationId) {
        super.copy(message);

        message.correlationId = correlationId;
    }

    public ResponseMessage copy(String correlationId) {
        AbstractResponseMessage message = (AbstractResponseMessage)copy();

        message.correlationId = correlationId;

        return message;
    }

    public StringBuilder toString(StringBuilder sb) {
        return super.toString(sb).append("\n    correlationId=").append(correlationId);
    }
}


