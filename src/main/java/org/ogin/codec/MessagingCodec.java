package org.ogin.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ogin
 */
public interface MessagingCodec<M> {
    enum ClientType {
        JAVA,
        AS3;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    ClientType getClientType();

    String getContentType();

    void encode(M message, OutputStream output) throws IOException;
    M decode(InputStream input) throws IOException;
}
