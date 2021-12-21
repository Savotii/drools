package com.spirent.drools.dto.kpi.type;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
public enum KpiType {
    HOST_LATENCY("hostLatency");

    private final String type;

    KpiType(String t) {
        type = t;
    }

    public String getType() {
        return type;
    }
}
