package com.spirent.drools.exception;

/**
 * @author ysavi2
 * @since 22.12.2021
 */
public class NoBaseThresholdException extends RuntimeException {
    public NoBaseThresholdException() {
        super();
    }

    public NoBaseThresholdException(String message) {
        super(message);
    }
}
