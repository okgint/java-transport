package org.ogin.channel;

import org.ogin.events.*;

/**
 * @author ogin
 */
public interface ResponseListener {
    void onResult(ResultEvent event);
    void onFault(FaultEvent event);
    void onFailure(FailureEvent event);
    void onTimeout(TimeoutEvent event);
    void onCancelled(CancelledEvent event);
}
