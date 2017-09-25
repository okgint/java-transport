package org.ogin.messages.responses;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

/**
 * @author ogin
 */
public class ResultMessage extends AbstractResponseMessage {
    private Object result;

    public ResultMessage() {
    }
    public ResultMessage(String clientId, String correlationId, Object result) {
        super(clientId, correlationId);

        this.result = result;
    }

    public ResultMessage(
            String id,
            String clientId,
            long timestamp,
            long timeToLive,
            Map<String, Object> headers,
            String correlationId,
            Object result) {

        super(id, clientId, timestamp, timeToLive, headers, correlationId);

        this.result = result;
    }

    public Type getType() {
        return Type.RESULT;
    }

    public Object getData() {
        return result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResultMessage copy() {
        ResultMessage message = new ResultMessage();

        super.copy(message);

        message.result = result;

        return message;
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);

        this.result = in.readObject();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        out.writeObject(result);
    }

    public StringBuilder toString(StringBuilder sb) {
        return super.toString(sb).append("\n    result=").append(result);
    }


}
