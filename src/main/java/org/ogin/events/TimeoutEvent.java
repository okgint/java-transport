package org.ogin.events;

import org.ogin.messages.RequestMessage;

/**
 * @author ogin
 */
public class TimeoutEvent extends AbstractIssueEvent {
    private final long time;

    public TimeoutEvent(RequestMessage request, long time) {
        super(request);
        this.time = time;
    }

    public Type getType() {
        return Type.TIMEOUT;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return getClass().getName() + " {timestamp=" + getRequest().getTimestamp() + " + timeToLive=" + getRequest().getTimeToLive() + " > time=" + time + "}";
    }
}
