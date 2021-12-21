package com.spirent.drools.service.rule.impl;

import com.spirent.drools.config.mapper.OrikaMapperConfig;
import com.spirent.drools.converter.RuleContentConverter;
import com.spirent.drools.dto.rules.Rule;
import com.spirent.drools.model.rule.RuleModel;
import com.spirent.drools.repository.RuleRepository;
import com.spirent.drools.service.ruleengine.RulesEngineService;
import com.spirent.drools.service.rule.RulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ysavi2
 * @since 10.12.2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RulesServiceImpl implements RulesService {
    private final OrikaMapperConfig orikaMapper;
    private final RuleContentConverter contentConverter;
    private final RuleRepository ruleRepository;
    private final RulesEngineService droolsEngine;

    @PostConstruct
    public void uploadAllRulesFromDb() {
        droolsEngine.addRulesToTheFileSystem(getRulesContent(ruleRepository.findAll()));
    }

    private Map<String, String> getRulesContent(List<RuleModel> ruleModels) {
        return ruleModels.stream()
                .collect(Collectors.toMap(RuleModel::getRuleKey, model -> contentConverter.clearSpecialSymbols(model.getContent())));
    }

    public Rule saveRule(Rule rule) {
        RuleModel model = ruleRepository.save(orikaMapper.map(rule, RuleModel.class));
        reloadByRule(model);
        return orikaMapper.map(model, Rule.class);
    }

    @Transactional
    public void updateRule(Rule rule) {
        Optional<RuleModel> ruleModel = ruleRepository.findById(rule.getId());

        if (ruleModel.isPresent()) {
            RuleModel model = ruleRepository.save(mergeModels(ruleModel.get(), rule));
            reloadByRule(model);
        }
    }

    private RuleModel mergeModels(RuleModel origin, Rule update) {
        return
                origin
                        .setRuleKey(update.getName())
                        .setVersion(update.getVersion())
                        .setCreateTime(update.getCreateTime())
                        .setLastModifyTime(update.getLastModifyTime() == null ? Instant.now().toString() : update.getLastModifyTime())
                        .setContent(contentConverter.convertToString(update.getRuleContent()));
    }

    public List<Rule> getAllRules() {
        List<RuleModel> result = ruleRepository.findAll(Sort.by(Sort.Order.desc("id")));
        result.forEach(r -> orikaMapper.map(r, Rule.class));
        return orikaMapper.mapAsList(result, Rule.class);
    }

    private void reloadByRule(RuleModel ruleModel) {
        droolsEngine.addRuleToTheFileSystem(ruleModel.getRuleKey(), contentConverter.clearSpecialSymbols(ruleModel.getContent()));
    }

    public void deleteRule(long id) {
        Optional<RuleModel> model = ruleRepository.findById(id);
        if (model.isPresent()) {
            droolsEngine.removeRuleFromSystem(model.get().getRuleKey());
            ruleRepository.deleteById(id);
        }
    }

    @Override
    //todo implement.
    public Optional<Rule> getRuleById(long id) {
        return Optional.empty();
    }
}
