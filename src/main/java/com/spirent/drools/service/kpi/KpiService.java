package com.spirent.drools.service.kpi;

import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.dto.rules.filter.KpiThresholdFilter;

import java.util.List;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
public interface KpiService {
    @Deprecated
    Kpi validateRules(Kpi incoming);

    AlertEvent validateRules(KpiRequest request);

    List<KpiThresholdFilter> getFilters();

    void updateFilters();
}
