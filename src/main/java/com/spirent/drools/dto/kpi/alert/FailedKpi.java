package com.spirent.drools.dto.kpi.alert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spirent.drools.dto.kpi.type.KpiType;
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
    private String name;

    @JsonIgnore
    private KpiType type;
    private long latency;
    private long threshold;
}
