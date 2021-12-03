package com.spirent.drools.service.impl;

import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.service.KpiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KpiServiceImpl implements KpiService {

    private final RulesServiceImpl rulesServiceImpl;

    @Override
    public Kpi validateRules(Kpi model) {
        rulesServiceImpl.reload();
        KieSession session = RulesServiceImpl.getKieContainer().newKieSession();
        session.insert(model);
        session.fireAllRules();
        session.dispose();
        return model;
    }
}
