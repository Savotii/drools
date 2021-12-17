package com.spirent.drools.service.impl;

import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.rules.globals.GlobalBoolean;
import com.spirent.drools.dto.rules.globals.GlobalRuleCounter;
import com.spirent.drools.listeners.TrackingAgendaEventListener;
import com.spirent.drools.service.RulesEngineService;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ysavi2
 * @since 17.12.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DroolsEngineServiceImpl implements RulesEngineService {
    private static Integer counter = 1;
    private static String groupId = "com.spirent";
    private static String artifactId = "drools";
    private static AtomicInteger version = new AtomicInteger(0);
    public static final String SRC_MAIN_RESOURCES_PATH = "src/main/resources/";
    public static final String DRL_EXTENSION = ".drl";
    //    private KieContainer container;
//    private KieFileSystem kfs;
    private KieServices services = KieServices.Factory.get();
    //    private KieRepository kieRepository;
//    private KieSession session;
//    private KieBase kieBase;
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
        setGlobalVariables(session);
    }

    @Override
    public void addRuleToTheFileSystem(String ruleKey, String drlContent) {
        kfs.write(getPath(ruleKey), drlContent);
        rebuildFileSystem();
        rebuildContainerAndSession();
        setGlobalVariables(session);
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
    public void fireAllRules(Kpi kpiRequest) {
        TrackingAgendaEventListener ruleListener = new TrackingAgendaEventListener();
        session.insert(kpiRequest);
        session.fireAllRules();
        List<Match> matchList = ruleListener.getMatchList();
        matchList.forEach(m -> log.info("Match info {}", m.toString()));
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
        session = container.newKieSession();
    }

    private void rebuildFileSystem() {
        KieBuilder kieBuilder = services.newKieBuilder(kfs);
        kieBuilder.buildAll();
    }

    private void setGlobalVariables(KieSession session) {
        session.setGlobal("flag", new GlobalBoolean());
        session.setGlobal("counter", new GlobalRuleCounter());
    }
}