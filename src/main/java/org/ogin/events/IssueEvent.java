package org.ogin.events;

import org.ogin.messages.RequestMessage;

/**
 * @author ogin
 */
public interface IssueEvent extends Event {
    RequestMessage getRequest();
}
