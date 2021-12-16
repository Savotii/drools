package com.spirent.drools.dto.alert;

/**
 * @author ysavi2
 * @since 16.12.2021
 */
public enum AlertLevel {
    CRITICAL("critical");

    private final String level;

    AlertLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
