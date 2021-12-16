package com.spirent.drools.dto.alert;

import com.spirent.drools.dto.kpi.FailedKpi;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

/**
 * @author ysavi2
 * @since 16.12.2021
 */
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class AlertEvent {
    private Instant timestamp;
    private String id;
    private String name;
    private AlertLevel level;
    private String agentId;
    private String agentTestName;
    private String testSessionId;
    private String testId;
    private String agentTestId;
    private String workflowId;
    private String overlayId;

    //todo we do not know where is it from. by default will be equal to testId
    private String networkElementId;

    private AlertCategory category;
    private String pkg;
    private String testName;
    private List<FailedKpi> failedKpis;
}