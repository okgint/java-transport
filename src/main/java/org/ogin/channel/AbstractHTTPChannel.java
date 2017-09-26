package org.ogin.channel;

import org.ogin.messages.requests.DisconnectMessage;
import org.ogin.messages.requests.PingMessage;
import org.ogin.messages.responses.ResultMessage;
import org.ogin.transport.Transport;

import java.net.URI;
import java.util.Timer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author ogin
 */
public class AbstractHTTPChannel extends AbstractChannel<Transport> implements Runnable{
    private final BlockingQueue<AsyncToken> tokensQueue = new LinkedBlockingQueue<AsyncToken>();
    private final ConcurrentMap<String, AsyncToken> tokensMap = new ConcurrentHashMap<String, AsyncToken>();
    private final AsyncToken stopToken = new AsyncToken(new DisconnectMessage());
    private final AtomicBoolean stopped = new AtomicBoolean(true);
    private AsyncToken disconnectToken = null;

    private Thread senderThread = null;
    private Semaphore connections;
    private Timer timer = null;

    private volatile boolean pinged = false;


    public AbstractHTTPChannel(Transport tranport, String id, URI uri) {
        super(tranport, id, uri);
    }

    public synchronized boolean start() {
        if(senderThread != null)
            return true;

        tokensQueue.clear();
        tokensMap.clear();
        disconnectToken = null;

        stopped.set(false);

        senderThread = new Thread(this, id + "_sender");
        try {
            timer = new Timer(id + "_timer", true);
            connections = new Semaphore(maxConcurrentRequests);
            senderThread.start();  // 0. Jalanin Event Loop
        }
    }
    public void run() {
        while (!Thread.interrupted()) {
            try {
                if(stopped.get())
                    break;
                AsyncToken token = tokensQueue.take();
                if(token == stopToken)
                    break;
                if(token.isDone())
                    continue;
                if (token.isDisconnectRequest()) {
                    sendToken(token);
                    continue;
                }

                executeReauthenticateCallback();
                if (!pinged) {  //1. Send PingMessage
                    PingMessage pingMessage = new PingMessage(clientId);
                    System.out.printf("Channel %s send ping %s with clientId %s", id, pingMessage.getId(), clientId);
                    ResultMessage result = sendBlockingToken(pingMessage, token);
                    if (result == null)
                        continue;
                    clientId = result.getClientId();
                    System.out.printf("Channel %s pinged clientId %s", id, clientId);
                    pinged = true;
                }

                authenticate(token); //2. Kirim Authentikasi
                if (!(token.getRequest() instanceof PingMessage)) //3. Kirim OTHER Message
                    sendToken(token);

            }
            catch (InterruptedException e) {
                System.out.printf("Channel %s stopped.", id);
                break;
            }
            catch (Exception e) {
                System.err.printf(e + "Channel %s got an unexpected exception.", id);
            }

        }
    }
}
