package com.spirent.drools.repository;

import com.spirent.drools.model.rule.RuleModel;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Order(1)
public interface RuleRepository extends JpaRepository<RuleModel, Long> {
    RuleModel findByRuleKey(String ruleKey);
}