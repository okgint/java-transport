package org.ogin.messages.responses;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;

/**
 * @author ogin
 */
public class FaultMessage extends AbstractResponseMessage {
    public static enum Code {
        UNKNOWN,

        CLIENT_CALL_FAILED,
        CLIENT_CALL_TIMED_OUT,
        CLIENT_CALL_CANCELLED,

        SERVER_CALL_FAILED,

        INVALID_CREDENTIALS,
        AUTHENTICATION_FAILED,
        NOT_LOGGED_IN,
        SESSION_EXPIRED,
        ACCESS_DENIED,

        VALIDATION_FAILED,
        OPTIMISTIC_LOCK
    }
    private Code code;
    private String description;
    private String details;
    private Object cause;
    private Map<String, Object> extended;

    private String unknownCode;

    public FaultMessage() {
    }

    public FaultMessage(
            String clientId,
            String correlationId,
            Code code,
            String description,
            String details,
            Object cause,
            Map<String, Object> extended) {

        super(clientId, correlationId);

        this.code = code;
        this.description = description;
        this.details = details;
        this.cause = cause;
        this.extended = extended;
    }

    public FaultMessage(
            String id,
            String clientId,
            long timestamp,
            long timeToLive,
            Map<String, Object> headers,
            String correlationId,
            Code code,
            String description,
            String details,
            Object cause,
            Map<String, Object> extended) {

        super(id, clientId, timestamp, timeToLive, headers, correlationId);

        this.code = code;
        this.description = description;
        this.details = details;
        this.cause = cause;
        this.extended = extended;
    }

    public Type getType() {
        return Type.FAULT;
    }

    public boolean isSecurityFault() {
        switch (code) {
            case UNKNOWN:
                return unknownCode != null && unknownCode.startsWith("Server.Security.");

            case INVALID_CREDENTIALS:
            case AUTHENTICATION_FAILED:
            case NOT_LOGGED_IN:
            case SESSION_EXPIRED:
            case ACCESS_DENIED:
                return true;

            default:
                return false;
        }
    }

    public Object getData() {
        return toString();
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Object getCause() {
        return cause;
    }

    public void setCause(Object cause) {
        this.cause = cause;
    }

    public Map<String, Object> getExtended() {
        return extended;
    }

    public void setExtended(Map<String, Object> extended) {
        this.extended = extended;
    }

    public String getUnknownCode() {
        return unknownCode;
    }

    public void setUnknownCode(String unknownCode) {
        this.unknownCode = unknownCode;
    }

    public FaultMessage copy() {
        FaultMessage message = new FaultMessage();

        super.copy(message);

        message.code = code;
        message.description = description;
        message.details = details;
        message.cause = cause;
        message.extended = extended;

        return message;
    }

    @SuppressWarnings("unchecked")
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);

        code = (Code)in.readObject();
        description = in.readUTF();
        details = in.readUTF();
        cause = in.readObject();
        extended = (Map<String, Object>)in.readObject();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        out.writeObject(code);
        if (description != null)
            out.writeUTF(description);
        else
            out.writeObject(null);
        if (details != null)
            out.writeUTF(details);
        else
            out.writeObject(null);
        out.writeObject(cause);
        out.writeObject(extended);
    }

    public StringBuilder toString(StringBuilder sb) {
        return super.toString(sb)
                .append("\n    code=").append(code)
                .append("\n    description=").append(description)
                .append("\n    details=").append(details)
                .append("\n    cause=").append(cause)
                .append("\n    extended=").append(extended);
    }
}
