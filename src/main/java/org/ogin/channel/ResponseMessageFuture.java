package org.ogin.channel;

import org.ogin.messages.ResponseMessage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author ogin
 */
public interface ResponseMessageFuture {
    public boolean cancel();
    public ResponseMessage get() throws InterruptedException, ExecutionException, TimeoutException;
    public ResponseMessage get(long timeout) throws InterruptedException, ExecutionException, TimeoutException;

    public boolean isCancelled();
    public boolean isDone();
}
