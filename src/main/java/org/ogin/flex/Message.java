package org.ogin.flex;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ogin
 */
public interface Message extends Serializable {

    public static final String ENDPOINT_HEADER = "DSEndpoint";
    public static final String CREDENTIALS_CHARSET_HEADER = "DSCredentialsCharset";
    public static final String REMOTE_CREDENTIALS_HEADER = "DSRemoteCredentials";
    public static final String REMOTE_CREDENTIALS_CHARSET_HEADER = "DSRemoteCredentialsCharset";
    public static final String DS_ID_HEADER = "DSId";

    public static final String HIDDEN_CREDENTIALS = "****** (credentials)";

    public Object getBody();

    public Object getClientId();

    public String getDestination();

    public Object getHeader(String name);

    public Map<String, Object> getHeaders();

    public String getMessageId();

    public long getTimestamp();

    public long getTimeToLive();

    public boolean headerExists(String name);

    public void setBody(Object value);

    public void setClientId(Object value);

    public void setDestination(String value);

    public void setHeader(String name, Object value);

    public void setHeaders(Map<String, Object> value);

    public void setMessageId(String value);

    public void setTimestamp(long value);

    public void setTimeToLive(long value);

    public String toString(String indent);
}