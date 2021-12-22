package com.spirent.drools.dto.kpi;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Data
@Accessors(chain = true)
public class KpiLatency {
    private long threshold;
    private String id;
    private long latency;
}
