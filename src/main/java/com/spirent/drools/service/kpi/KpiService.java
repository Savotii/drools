package com.spirent.drools.service.kpi;

import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
public interface KpiService {
    Kpi validateRules(Kpi incoming);

    AlertEvent validateRules(KpiRequest request);
}
