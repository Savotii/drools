package com.spirent.drools.service;

import com.spirent.drools.dto.rules.Rule;

import java.util.List;
import java.util.Optional;

/**
 * @author ysavi2
 * @since 03.12.2021
 *
 * Interface provides all necessary methods for rules needs.
 */
public interface RulesService {
    Rule saveRule(Rule rule);

    void updateRule(Rule rule);

    List<Rule> getAllRules();

    Optional<Rule> getRuleById(long id);

    void deleteRule(long id);
}
