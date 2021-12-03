package com.spirent.drools.config;

import com.spirent.drools.converter.RuleContentConverter;
import com.spirent.drools.dto.rules.Rule;
import com.spirent.drools.model.RuleModel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Configuration;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@Configuration
@RequiredArgsConstructor
public class OrikaMapperConfig extends ConfigurableMapper {
    private final RuleContentConverter contentConverter;

    protected void configure(MapperFactory factory) {
        factory.classMap(RuleModel.class, Rule.class)
                .byDefault()
                .customize(new RuleModelToRuleConverter())
                .register();
    }

    public class RuleModelToRuleConverter extends CustomMapper<RuleModel, Rule> {
        @Override
        public void mapAtoB(RuleModel ruleModel, Rule rule, MappingContext mappingContext) {
            rule.setName(ruleModel.getRuleKey());
            rule.setRuleContent(contentConverter.convertFromString(ruleModel.getContent()));
        }

        @Override
        public void mapBtoA(Rule rule, RuleModel ruleModel, MappingContext context) {
            ruleModel.setRuleKey(rule.getName());
            ruleModel.setContent(contentConverter.convertToString(rule.getRuleContent()));
        }
    }
}
