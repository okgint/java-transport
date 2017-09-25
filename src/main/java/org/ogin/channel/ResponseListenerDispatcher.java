package org.ogin.channel;

import org.ogin.events.*;
import org.ogin.messages.ResponseMessage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author ogin
 */
public final class ResponseListenerDispatcher {
    private ResponseListenerDispatcher() {
        throw new RuntimeException("Not instanciable");
    }

    public static void dispatch(ResponseListener listener, Event event) {
        if (listener == null || event == null)
            throw new NullPointerException("listener and event cannot be null");

        boolean unknownEventType = false;

        try {
            switch (event.getType()) {
                case RESULT:
                    listener.onResult((ResultEvent)event);
                    break;
                case FAULT:
                    listener.onFault((FaultEvent)event);
                    break;
                case FAILURE:
                    listener.onFailure((FailureEvent)event);
                    break;
                case TIMEOUT:
                    listener.onTimeout((TimeoutEvent)event);
                    break;
                case CANCELLED:
                    listener.onCancelled((CancelledEvent)event);
                    break;
                default:
                    unknownEventType = true;
                    break;
            }
        }
        catch (Exception e) {
            System.err.printf("ResponseListener %s threw an exception for event %s", listener, event);
        }

        if (unknownEventType) {
            RuntimeException e = new RuntimeException("Unknown event type: " + event);
            System.err.println(e +  "");
            throw e;
        }
    }

    public static ResponseMessage getResponseMessage(Event event) throws InterruptedException, ExecutionException, TimeoutException {
        if (event == null)
            throw new NullPointerException("event cannot be null");

        switch (event.getType()) {
            case RESULT: case FAULT:
                return ((AbstractResponseEvent<?>)event).getResponse();
            case FAILURE:
                throw new ExecutionException(((FailureEvent)event).getCause());
            case TIMEOUT:
                throw new TimeoutException(((TimeoutEvent)event).toString());
            case CANCELLED:
                throw new InterruptedException(((CancelledEvent)event).toString());
            default: {
                RuntimeException e = new RuntimeException("Unknown event type: " + event);
                System.err.println(e + "");
                throw e;
            }
        }
    }
}
