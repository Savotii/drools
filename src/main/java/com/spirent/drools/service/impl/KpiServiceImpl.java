package com.spirent.drools.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirent.drools.config.mapper.OrikaMapperConfig;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.messagebroker.producer.Producer;
import com.spirent.drools.model.kpi.KpiModel.KpiModel;
import com.spirent.drools.repository.KpiRepository;
import com.spirent.drools.service.KpiService;
import com.spirent.drools.service.RulesEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KpiServiceImpl implements KpiService {

    private final KpiRepository kpiRepository;
    private final Producer kafkaProducer;
    private final ObjectMapper mapper;
    private final OrikaMapperConfig orikaMapper;
    private final RulesEngineService droolsEngine;

    @Override
    public Kpi validateRules(Kpi kpiRequest) {
        droolsEngine.fireAllRules(kpiRequest);
        if (kpiRequest.isFailed()) {
            kafkaProducer.sendFailureMessage(convertToJson(kpiRequest));
        } else {
            kafkaProducer.sendSuccessMessage(convertToJson(kpiRequest));
        }

        // just put it into the db.
        kpiRepository.save(orikaMapper.map(kpiRequest, KpiModel.class));
        return kpiRequest;
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
