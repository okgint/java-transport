package org.ogin.messages;

/**
 * @author ogin
 */
public interface ResponseMessage extends Message, MessageChain<ResponseMessage>{
    String getCorrelationId();

    void setCorrelationId(String correlationId);

    boolean isProcessed();

    ResponseMessage copy(String correlationId);

    Object getData();
}
