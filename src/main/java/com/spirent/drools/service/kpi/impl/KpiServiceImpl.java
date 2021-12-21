package com.spirent.drools.service.kpi.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirent.drools.config.mapper.OrikaMapperConfig;
import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.alert.FailedKpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.model.kpi.KpiModel.KpiModel;
import com.spirent.drools.repository.KpiRepository;
import com.spirent.drools.service.alert.AlertService;
import com.spirent.drools.service.kpi.KpiService;
import com.spirent.drools.service.notification.NotificationService;
import com.spirent.drools.service.ruleengine.RulesEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.rule.Match;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KpiServiceImpl implements KpiService {

    private final AlertService alertService;
    private final KpiRepository kpiRepository;
    private final ObjectMapper mapper;
    private final OrikaMapperConfig orikaMapper;
    private final RulesEngineService droolsEngine;

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
        List<Match> matches = droolsEngine.fireAllRules(request);
        if (!matches.isEmpty()) {
            return buildAlertMessageAndSend(request, matches);
        }
        return null;
    }

    private AlertEvent buildAlertMessageAndSend(KpiRequest request, List<Match> matches) {
        AlertEvent alert = orikaMapper.map(request, AlertEvent.class);
        alert.setFailedKpis(alertService.buildFailedKpiLatencyAlert(matches));
        alertService.saveEvent(alert);
        alertService.send(alert);
        return alert;
    }

    private void buildAlertMessageAndSend(List<Match> matchList) {
//        new AlertEvent()
//                .setTimestamp(Instant.now())
//                .setCategory(alertService.getCategory())
//                .setLevel(alertService.getAlertLevel())
//                .setAgentId("1")
//                .setAgentTestId("1")
//                .setId("1")
//                .setName("1")
//                .setNetworkElementId("1")
//                .setOverlayId("1")
//                .setPkg("1")
//                .setTestId("1")
//                .setTestName("1")
//                .setTestSessionId("1")
//                .setWorkflowId("1");
    }


    private String convertToJson(Kpi model) {
        try {
            return mapper.writeValueAsString(model);
        } catch (JsonProcessingException e) {
            log.error("[KpiServiceImpl] convert to json failed.", e);
            return null;
        }
    }

}
