package com.spirent.drools.dto.kpi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author ysavi2
 * @since 16.12.2021
 */
@Data
@ToString
@EqualsAndHashCode
public class FailedKpi {
    private long id;
    private TestType type;
    private long latency;
    private long threshold;
}
