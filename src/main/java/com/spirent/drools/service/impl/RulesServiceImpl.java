package com.spirent.drools.service.impl;

import com.spirent.drools.config.mapper.OrikaMapperConfig;
import com.spirent.drools.converter.RuleContentConverter;
import com.spirent.drools.dto.rules.Rule;
import com.spirent.drools.model.rule.RuleModel;
import com.spirent.drools.repository.RuleRepository;
import com.spirent.drools.service.RulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Created by neo on 17/7/31.
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RulesServiceImpl implements RulesService {

    private static KieContainer kieContainer;
    private final OrikaMapperConfig orikaMapper;
    private final RuleContentConverter contentConverter;
    private final RuleRepository ruleRepository;

    public static void setKieContainer(KieContainer newContainer) {
        kieContainer = newContainer;
    }

    public static KieContainer getKieContainer() {
        return kieContainer;
    }

    public void reload(){
        setKieContainer(loadContainerFromString(ruleRepository.findAll()));
    }

    public Rule saveRule(Rule rule) {
        RuleModel result = ruleRepository.save(orikaMapper.map(rule, RuleModel.class));
        reload();
        return orikaMapper.map(result, Rule.class);
    }

    @Transactional
    public void updateRule(Rule rule) {
        ruleRepository.findById(rule.getId())
                .ifPresentOrElse(ruleModel -> ruleRepository.save(mergeModels(ruleModel, rule)), () -> {
                            /* todo something. */
                        });
        reload();
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

    private  KieContainer loadContainerFromString(List<RuleModel> ruleModels) {
        long startTime = System.currentTimeMillis();
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        KieFileSystem kfs = ks.newKieFileSystem();

        for (RuleModel ruleModel : ruleModels) {
            String  drl= contentConverter.clearSpecialSymbols(ruleModel.getContent());
            kfs.write("src/main/resources/" + drl.hashCode() + ".drl", drl);
        }

        KieBuilder kb = ks.newKieBuilder(kfs);

        kb.buildAll();
        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Build Errors:\n" + kb.getResults().toString());
        }
        long endTime = System.currentTimeMillis();
        log.info("Time to build rules : {} ms.",(endTime - startTime) );
        startTime = System.currentTimeMillis();
        KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
        log.info("Time to load container: {} ms.", (System.currentTimeMillis() - startTime));
        return kContainer;
    }

    public void deleteRule(long id) {
        //todo check if it was deleted throw an exception.
        ruleRepository.deleteById(id);
        reload();
    }

    @Override
    //todo implement.
    public Optional<Rule> getRuleById(long id) {
        return Optional.empty();
    }
}
