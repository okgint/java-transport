package org.ogin.messages.requests;

import java.util.Map;

/**
 * @author ogin
 */
public class LoginMessage extends AbstractRequestMessage {
    public LoginMessage() {
    }

    public LoginMessage(String clientId) {
        super(clientId);
    }

    public LoginMessage(String id, String clientId, long timestamp, long timeToLive, Map<String, Object> headers) {
        super(id, clientId, timestamp, timeToLive, headers);
    }

    public Type getType() {
        return Type.LOGIN;
    }

}
