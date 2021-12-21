package com.spirent.drools.dto.kpi;

import com.spirent.drools.dto.kpi.type.KpiType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Data
@Accessors(chain = true)
public class KpiLatency {
    private final long THRESHOLD = 1000;
    private String id;
    private long latency;
}
