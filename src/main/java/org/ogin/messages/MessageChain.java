package org.ogin.messages;

import java.util.Iterator;

/**
 * @author ogin
 */
public interface MessageChain<T extends MessageChain<T>> extends Message, Iterable<T> {
    void setNext(T next);
    T getNext();
}
