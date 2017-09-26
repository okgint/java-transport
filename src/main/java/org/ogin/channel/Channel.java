package org.ogin.channel;

import org.ogin.messages.RequestMessage;
import org.ogin.transport.Transport;
import org.ogin.transport.TransportMessage;

import java.io.InputStream;
import java.net.URI;

/**
 * @author ogin
 */
public interface Channel {
    Transport getTransport();
    String getId();
    URI getUri();
    String getClientId();
    long getDefaultTimeToLive();
    void setDefaultTimeToLive(long defaultTimeToLive);

    /**
     * Start the channel. This is where the channel can initialize its internal resources, thread pools...
     * @return true if the channel has correctly started
     */
    boolean start();
    boolean isStarted();
    boolean stop();

    boolean isAuthenticated();

    /**
     * Send a message on this channel
     * @param request message to send
     * @param listeners array of listeners to notify asynchronously when the response is received
     * @return future that will be triggered when the response is received
     */
    ResponseMessageFuture send(RequestMessage request, ResponseListener... listeners);
    ResponseMessageFuture logout(ResponseListener... listeners);
    ResponseMessageFuture logout(boolean sendLogout, ResponseListener... listeners);
    <D> D getTransportData();
    void setTransportData(Object data);
    void onMessage(TransportMessage message, InputStream is);
    void onDisconnect();
    void onError(TransportMessage message, Exception e);
    void onCancelled(TransportMessage message);
    public void preconnect() throws ChannelException;
    public TransportMessage createConnectMessage(String id, boolean reconnect);
}
