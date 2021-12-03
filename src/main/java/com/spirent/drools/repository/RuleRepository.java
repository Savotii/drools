package com.spirent.drools.repository;

import com.spirent.drools.model.RuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<RuleModel, Long> {
    RuleModel findByRuleKey(String ruleKey);
}