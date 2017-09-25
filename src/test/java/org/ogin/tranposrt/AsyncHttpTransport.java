package org.ogin.tranposrt;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.junit.Before;
import org.junit.Test;
import org.ogin.transport.Transport;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * @author ogin
 */
public class AsyncHttpTransport{
    private CloseableHttpAsyncClient httpClient;
    private boolean isStarted = false;

    @Before
    public void init() {
        httpClient = HttpAsyncClients.createDefault();
    }
    public boolean start() {
        httpClient.start();
        isStarted = true;
        return isStarted;
    }

    public boolean isStart() {
        return isStarted;
    }

    public void stop() {
        try {
            httpClient.close();
            isStarted = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReconnectAfterReceive() {
        return false;
    }

    public boolean isDisconnectAfterAuthenticationFailure() {
        return false;
    }

    public boolean isAuthenticationAfterReconnectWithRemoting() {
        return false;
    }

    @Test
    public void get() throws ExecutionException, InterruptedException {
        start();
        final HttpGet request = new HttpGet("http://www.apache.org");
        Future<HttpResponse> future = httpClient.execute(request, null);

        HttpResponse response = future.get();
        assertEquals("", response.getStatusLine());
        stop();
    }
}
