package com.spirent.drools.service;

import com.spirent.drools.dto.kpi.Kpi;

import java.util.Map;

/**
 * @author ysavi2
 * @since 17.12.2021
 *
 * Service provides all necessary method for working with Drools etc
 */
public interface RulesEngineService {

    /**
     * Method adds the new rules to the drool's file system to be appealed by system
     * @param rulesContent Map<String, String>  of (RuleName, DrlFileContent)
     */
    void addRulesToTheFileSystem(Map<String, String> rulesContent);

    void addRuleToTheFileSystem(String ruleKey, String content);

    void removeRuleFromSystem(String ruleKey);

    void fireAllRules(Kpi kpiRequest);
}
