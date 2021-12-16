package com.spirent.drools.dto.alert;

/**
 * @author ysavi2
 * @since 16.12.2021
 */
public enum AlertCategory {
    VOICE("Voice");

    private final String category;

    AlertCategory(String type) {
        category = type;
    }

    public String getCategory() {
        return category;
    }
}
