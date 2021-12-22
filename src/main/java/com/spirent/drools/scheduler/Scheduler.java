package com.spirent.drools.scheduler;

import com.spirent.drools.service.kpi.KpiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ysavi2
 * @since 22.12.2021
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {
    private final KpiService kpiService;

    @Scheduled(fixedDelayString = "${scheduler.update_threshold_period}")
    public void updateFilters() {
        kpiService.updateFilters();
    }
}
