package com.spirent.drools.service.alert;

import com.spirent.drools.dto.alert.AlertCategory;
import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.alert.AlertLevel;
import com.spirent.drools.dto.kpi.alert.FailedKpi;
import com.spirent.drools.dto.rules.filter.KpiThresholdFilter;
import org.kie.api.runtime.rule.Match;

import java.util.List;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
public interface AlertService {

    /*
     * We do not know how should we evaluate the levels
     * they might be evaluated by different circumstances for instance: region, latency, threshold...
     */
    default AlertLevel getAlertLevel() {
        return AlertLevel.Critical; //for now by default.
    }

    default AlertCategory getCategory() {
        return AlertCategory.Voice;
    }

    void send(AlertEvent event);

    List<FailedKpi> buildFailedKpiLatencyAlert(List<Match> matches);

    void saveEvent(AlertEvent alert);
}
