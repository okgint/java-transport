package org.ogin.channel;

import org.ogin.events.*;
import org.ogin.messages.RequestMessage;
import org.ogin.messages.ResponseMessage;
import org.ogin.messages.requests.DisconnectMessage;
import org.ogin.messages.responses.FaultMessage;
import org.ogin.messages.responses.ResultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author ogin
 */
public class AsyncToken extends TimerTask implements ResponseMessageFuture {
    private final RequestMessage request;
    private final List<ResponseListener> listeners = new ArrayList<ResponseListener>();

    private Event event = null;

    private ResponseListener channelListener = null;

    public AsyncToken(RequestMessage request) {
        this(request, (ResponseListener[])null);
    }

    public AsyncToken(RequestMessage request, ResponseListener listener) {
        this(request, (listener == null ? null : new ResponseListener[]{listener}));
    }

    public AsyncToken(RequestMessage request, ResponseListener[] listeners) {
        if (request == null)
            throw new NullPointerException("request cannot be null");
        this.request = request;

        if (listeners != null) {
            for (ResponseListener listener : listeners) {
                if (listener == null)
                    throw new NullPointerException("listeners cannot contain null values");
                this.listeners.add(listener);
            }
        }
    }
    public String getId() {
        return request.getId();
    }

    public RequestMessage getRequest() {
        return request;
    }

    public boolean isDisconnectRequest() {
        return request instanceof DisconnectMessage;
    }

    public synchronized Event setChannelListener(ResponseListener channelListener) {
        if (event == null)
            this.channelListener = channelListener;
        return event;
    }

    public void run() {
        try {
            // Try to dispatch a TimeoutEvent.
            dispatchTimeout(System.currentTimeMillis());
        }
        catch (Throwable e) {
            System.err.println("Error while executing token task for request " + request);
        }
    }

    public boolean cancel() {
        // Try to dispatch a CancelledEvent.
        return dispatchCancelled();
    }

    public ResponseMessage get() throws InterruptedException, ExecutionException, TimeoutException {
        return get(0);
    }
    public ResponseMessage get(long timeout) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (event == null) {
                try {
                    wait(timeout);
                }
                catch (InterruptedException e) {
                    if (dispatchCancelled())
                        throw e;
                }
            }
        }

        return ResponseListenerDispatcher.getResponseMessage(event);
    }

    public synchronized boolean isCancelled() {
        return event instanceof CancelledEvent;
    }

    public synchronized boolean isDone() {
        return event != null;
    }
    public boolean dispatchResult(ResultMessage result) {
        return dispatch(new ResultEvent(request, result));
    }

    public boolean dispatchFault(FaultMessage fault) {
        return dispatch(new FaultEvent(request, fault));
    }

    public boolean dispatchFailure(Exception e) {
        return dispatch(new FailureEvent(request, e));
    }

    public boolean dispatchTimeout(long millis) {
        return dispatch(new TimeoutEvent(request, millis));
    }

    public boolean dispatchCancelled() {
        return dispatch(new CancelledEvent(request));
    }

    private boolean dispatch(Event event) {

        // Cancel this TimerTask.
        super.cancel();

        synchronized (this) {

            // Make sure we didn't dispatch a previous event.
            if (this.event != null)
                return false;

            // Create the corresponding event.
            this.event = event;

            if (channelListener != null)
                ResponseListenerDispatcher.dispatch(channelListener, event);

            // Wake up all threads waiting on the get() method.
            notifyAll();
        }

        // Call all listeners.
        for (ResponseListener listener : listeners)
            ResponseListenerDispatcher.dispatch(listener, event);

        // Release references on listeners to help gc
        channelListener = null;
        listeners.clear();

        return true;
    }
}
