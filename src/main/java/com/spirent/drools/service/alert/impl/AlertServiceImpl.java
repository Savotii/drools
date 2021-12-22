package com.spirent.drools.service.alert.impl;

import com.spirent.drools.config.mapper.OrikaMapperConfig;
import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.kpi.KpiLatency;
import com.spirent.drools.dto.kpi.alert.FailedKpi;
import com.spirent.drools.dto.rules.filter.KpiThresholdFilter;
import com.spirent.drools.model.alert.AlertModel;
import com.spirent.drools.repository.AlertRepository;
import com.spirent.drools.service.alert.AlertService;
import com.spirent.drools.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.rule.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {
    private final AlertRepository repository;
    private final OrikaMapperConfig orikaMapper;

    @Autowired
    @Qualifier("kafkaNotificationService")
    private NotificationService notificationService;

    @Override
    public void send(AlertEvent event) {
        notificationService.send(event);
    }

    /*
     * I suppose the cases may be much more many?
     */
    @Override
    public List<FailedKpi> buildFailedKpiLatencyAlert(List<Match> matches) {
        List<FailedKpi> result = new LinkedList<>();
        for (Match match : matches) {
            List<KpiLatency> kpis = match.getObjects().stream().map(KpiLatency.class::cast).toList();
            kpis.stream().map(kpl -> {
                FailedKpi failedKpi = new FailedKpi();
                failedKpi.setLatency(kpl.getLatency());
                failedKpi.setThreshold(kpl.getThreshold());
                failedKpi.setName(match.getRule().getName());
                return failedKpi;
            }).forEach(result::add);

        }
        return result;
    }

    @Override
    public void saveEvent(AlertEvent alert) {
        AlertModel model = orikaMapper.map(alert, AlertModel.class);
        model.setAlertName(alert.getName());
        model.getFailedKpis().forEach(f -> f.setAlert(model));
        repository.save(model);
    }
}
