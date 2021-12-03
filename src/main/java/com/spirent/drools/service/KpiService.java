package com.spirent.drools.service;

import com.spirent.drools.dto.kpi.Kpi;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
public interface KpiService {
    Kpi validateRules(Kpi incoming);
}
