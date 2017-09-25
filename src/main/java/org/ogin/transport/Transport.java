package org.ogin.transport;

/**
 * @author ogin
 */
public interface Transport {
    boolean start();
    boolean isStart();

    void stop();

    /**
     * Indicates to the calling Channel that the transport should reconnect when data has been received
     * @return true if the transport should reconnect
     */
    boolean isReconnectAfterReceive();
    /**
     * Indicates to the calling Channel that the transport should be closed after an authentication failure
     * @return true if the transport should close
     */
    boolean isDisconnectAfterAuthenticationFailure();

    /**
     * Indicates that authentication after reconnect should be delegated to a remoting channel
     * @return true if authentication delegated
     */
    boolean isAuthenticationAfterReconnectWithRemoting();
}
