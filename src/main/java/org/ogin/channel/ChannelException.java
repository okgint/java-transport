package org.ogin.channel;

/**
 * @author ogin
 */
public class ChannelException extends Exception {
    private final String clientId;

    public ChannelException(String clientId, String message) {
        super(message);
        this.clientId = clientId;
    }

    public ChannelException(String clientId, String message, Throwable cause) {
        super(message, cause);
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
