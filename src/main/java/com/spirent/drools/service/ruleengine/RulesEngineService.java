package com.spirent.drools.service.ruleengine;

import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import org.kie.api.runtime.rule.Match;

import java.util.List;
import java.util.Map;

/**
 * @author ysavi2
 * @since 17.12.2021
 * <p>
 * Service provides all necessary method for working with Drools etc
 */
public interface RulesEngineService {

    /**
     * Method adds the new rules to the drool's file system to be appealed by system
     *
     * @param rulesContent Map<String, String>  of (RuleName, DrlFileContent)
     */
    void addRulesToTheFileSystem(Map<String, String> rulesContent);

    void addRuleToTheFileSystem(String ruleKey, String content);

    void removeRuleFromSystem(String ruleKey);

    List<Match> fireAllRules(Kpi kpiRequest);

    List<Match> fireAllRules(KpiRequest kpiRequest);
}
