package org.ogin.transport;

import org.ogin.codec.MessagingCodec;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ogin
 */
public interface TransportMessage {
    MessagingCodec.ClientType getClientType();

    String getId();

    boolean isConnect();

    boolean isDisconnect();

    String getClientId();

    String getSessionId();

    String getContentType();

    void encode(OutputStream os) throws IOException;
}
