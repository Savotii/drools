package com.spirent.drools.service.ruleengine.impl;

import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.dto.rules.globals.GlobalBoolean;
import com.spirent.drools.dto.rules.globals.GlobalRuleCounter;
import com.spirent.drools.listeners.TrackingAgendaEventListener;
import com.spirent.drools.service.ruleengine.RulesEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ysavi2
 * @since 17.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DroolsEngineServiceImpl implements RulesEngineService {
    public static final String SRC_MAIN_RESOURCES_PATH = "src/main/resources/";
    public static final String DRL_EXTENSION = ".drl";
    private final KieServices services = KieServices.Factory.get();

    @Autowired
    private KieContainer container;
    @Autowired
    private KieSession session;
    private final KieFileSystem kfs;

    @Override
    public void addRulesToTheFileSystem(Map<String, String> rules) {
        rules.forEach((ruleName, drlContent) -> kfs.write(getPath(ruleName), drlContent));
        rebuildFileSystem();
        rebuildContainerAndSession();
        setGlobalVariables();
    }

    @Override
    public void addRuleToTheFileSystem(String ruleKey, String drlContent) {
        kfs.write(getPath(ruleKey), drlContent);
        rebuildFileSystem();
        rebuildContainerAndSession();
        setGlobalVariables();
        //todo check the globals
    }

    /**
     * Method removes the rule from its own inner repository.
     *
     * @param ruleKey - String rule key by which it lies in the db.
     */
    @Override
    public void removeRuleFromSystem(String ruleKey) {
        kfs.delete(ruleKey);
        rebuildFileSystem();
        rebuildContainerAndSession();
    }

    @Override
    public List<Match> fireAllRules(Kpi kpiRequest) {
        TrackingAgendaEventListener trackListener = new TrackingAgendaEventListener();
        session.addEventListener(trackListener);
        session.insert(kpiRequest);
        session.fireAllRules();
        List<Match> matches = new ArrayList<>(trackListener.getMatchList());
        trackListener.reset();
        return matches;
    }

    @Override
    public List<Match> fireAllRules(KpiRequest request) {
        TrackingAgendaEventListener trackListener = new TrackingAgendaEventListener();
        session.addEventListener(trackListener);
        request.getKpis().forEach(session::insert);
        session.fireAllRules();
        List<Match> matchList = new ArrayList<>(trackListener.getMatchList());
        trackListener.reset();
        return matchList;
    }

    private String getPath(String ruleKey) {
        return SRC_MAIN_RESOURCES_PATH + getRuleTextFileName(ruleKey) + DRL_EXTENSION;
    }

    public static String getRuleTextFileName(String ruleKey) {
        return ruleKey.replace(StringUtils.SPACE, "_");
    }

    private void rebuildContainerAndSession() {
        final KieRepository kieRepository = services.getRepository();
        container = services.newKieContainer(kieRepository.getDefaultReleaseId());
        session.destroy();
        session = container.newKieSession();
    }

    private void rebuildFileSystem() {
        KieBuilder kieBuilder = services.newKieBuilder(kfs);
        kieBuilder.buildAll();
    }

    private void setGlobalVariables() {
        try {
            session.setGlobal("flag", new GlobalBoolean());
            session.setGlobal("counter", new GlobalRuleCounter());
        } catch (Exception e) {
            log.error("[Drools service] Global variables were not set up.", e);
        }
    }
}