package org.ogin.util;

import java.util.UUID;

/**
 * @author ogin
 */
public abstract class UUIDUtil {
    public static String randomUUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }
}
