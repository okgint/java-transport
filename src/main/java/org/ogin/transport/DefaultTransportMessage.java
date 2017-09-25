package org.ogin.transport;

import org.ogin.codec.MessagingCodec;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ogin
 */
public class DefaultTransportMessage<M> implements TransportMessage {
    private final String id;
    private final boolean connect;
    private final boolean disconnect;
    private final String clientId;
    private final String sessionId;
    private final M message;
    private final MessagingCodec<M> codec;

    public DefaultTransportMessage(String id, boolean connect, boolean disconnect, String clientId, String sessionId, M message, MessagingCodec<M> codec) {
        this.id = id;
        this.connect = connect;
        this.disconnect = disconnect;
        this.clientId = clientId;
        this.sessionId = sessionId;
        this.message = message;
        this.codec = codec;
    }

    public MessagingCodec.ClientType getClientType() {
        return codec.getClientType();
    }

    public String getId() {
        return id;
    }

    public boolean isConnect() {
        return connect;
    }

    public boolean isDisconnect() {
        return disconnect;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getContentType() {
        return codec.getContentType();
    }

    public void encode(OutputStream os) throws IOException {
        codec.encode(message, os);
    }
}
