package com.spirent.drools.service.kpi.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirent.drools.config.mapper.OrikaMapperConfig;
import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.dto.rules.filter.KpiThresholdFilter;
import com.spirent.drools.model.kpi.KpiModel;
import com.spirent.drools.repository.KpiRepository;
import com.spirent.drools.repository.KpiThresholdFilterRepository;
import com.spirent.drools.service.alert.AlertService;
import com.spirent.drools.service.kpi.KpiService;
import com.spirent.drools.service.ruleengine.RulesEngineService;
import com.spirent.drools.util.RuleFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.rule.Match;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KpiServiceImpl implements KpiService {
    //todo extract it to the dedicated class
    private static final List<KpiThresholdFilter> filters = new ArrayList<>();

    private final AlertService alertService;
    private final KpiRepository kpiRepository;
    private final ObjectMapper mapper;
    private final OrikaMapperConfig orikaMapper;
    private final RulesEngineService droolsEngine;
    private final KpiThresholdFilterRepository filterRepository;

    @Override
    public Kpi validateRules(Kpi kpiRequest) {
        final List<Match> matches = droolsEngine.fireAllRules(kpiRequest);
        // just put it into the db.
        kpiRepository.save(orikaMapper.map(kpiRequest, KpiModel.class));
        if (!matches.isEmpty()) {
            buildAlertMessageAndSend(matches);
        }
        return kpiRequest;
    }


    @Override
    public AlertEvent validateRules(KpiRequest request) {
        RuleFilterUtil.populateThreshold(request, filters);
        List<Match> matches = droolsEngine.fireAllRules(request);
        if (!matches.isEmpty()) {
            return buildAlertMessageAndSend(request, matches);
        }
        return null;
    }

    private AlertEvent buildAlertMessageAndSend(KpiRequest request, List<Match> matches) {
        AlertEvent alert = orikaMapper.map(request, AlertEvent.class);
        alert.setAlertId(UUID.randomUUID().toString());
        alert.setFailedKpis(alertService.buildFailedKpiLatencyAlert(matches));
        alertService.saveEvent(alert);
        alertService.send(alert);
        return alert;
    }

    private void buildAlertMessageAndSend(List<Match> matchList) {
        //stub
    }

    @Override
    public List<KpiThresholdFilter> getFilters() {
        return List.copyOf(filters);
    }

    @Override
    public void updateFilters() {
        filters.clear();
        filters.addAll(filterRepository.findAll().stream().map(kpi -> new KpiThresholdFilter(kpi.getId(), kpi.getTestId(), kpi.getOverlayId(), kpi.getValue())).toList());
    }
}
