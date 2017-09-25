package org.ogin.channel;


import org.ogin.flex.CommandMessage;
import org.ogin.flex.Message;
import org.ogin.transport.DefaultTransportMessage;
import org.ogin.transport.Transport;
import org.ogin.transport.TransportMessage;

import java.net.URI;

/**
 * @author ogin
 */
public abstract class AbstractChannel<T extends Transport> implements Channel {
    protected final T tranpsort;
    protected final String id;
    protected final URI uri;

    protected volatile String clientId;
    protected volatile Object transportData = null;

    public AbstractChannel(T tranport, String id, URI uri) {
        if(tranport == null || id == null | uri == null)
            throw new NullPointerException("Transport, id, and uri must not be null");

        this.tranpsort = tranport;
        this.id = id;
        this.uri = uri;
    }

    public T getTranpsort() {
        return tranpsort;
    }

    public String getId() {
        return id;
    }

    public URI getUri() {
        return uri;
    }

    public <D> D getTransportData() {
        return (D)transportData;
    }
    public void setTransportData(Object data) {
        this.transportData = data;
    }

    public void preconnect() throws ChannelException {
    }

    public TransportMessage createConnectMessage(String id, boolean reconnect) {
        CommandMessage connectMessage = new CommandMessage();
        connectMessage.setOperation(CommandMessage.CONNECT_OPERATION);
        connectMessage.setMessageId(id);
        connectMessage.setTimestamp(System.currentTimeMillis());
        connectMessage.setClientId(clientId);

        return new DefaultTransportMessage<Message[]>(id, !reconnect, false, clientId, null, new Message[] { connectMessage }, null);
    }
}
