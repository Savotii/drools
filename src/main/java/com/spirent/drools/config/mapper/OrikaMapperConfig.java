package com.spirent.drools.config.mapper;

import com.spirent.drools.converter.RuleContentConverter;
import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.alert.AlertLevel;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.alert.FailedKpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.dto.rules.Rule;
import com.spirent.drools.model.alert.AlertModel;
import com.spirent.drools.model.alert.FailedKpiModel;
import com.spirent.drools.model.kpi.KpiModel.KpiModel;
import com.spirent.drools.model.rule.RuleModel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
        factory.classMap(Kpi.class, KpiModel.class)
                .byDefault()
                .register();
        factory.classMap(KpiRequest.class, AlertEvent.class)
                .byDefault()
                .customize(new KpiRequestToAlertEvent())
                .register();
        factory.classMap(AlertModel.class, AlertEvent.class)
                .byDefault()
                .customize(new AlertToAlertModel())
                .register();
        factory.classMap(FailedKpiModel.class, FailedKpi.class)
                .byDefault()
                .register();
    }

    public class AlertToAlertModel extends CustomMapper<AlertModel, AlertEvent> {
        @Override
        public void mapAtoB(AlertModel alertModel, AlertEvent alertEvent, MappingContext context) {
            List<FailedKpiModel> failedKpiModels = mapperFacade.mapAsList(alertEvent.getFailedKpis(), FailedKpiModel.class);
//            failedKpiModels.forEach(f -> f.setAlertId(alertModel.getId()));
            alertModel.setFailedKpis(failedKpiModels);
        }
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

    public class KpiRequestToAlertEvent extends CustomMapper<KpiRequest, AlertEvent> {
        @Override
        public void mapAtoB(KpiRequest request, AlertEvent alertEvent, MappingContext mappingContext) {
            alertEvent.setLevel(AlertLevel.Critical);
            alertEvent.setCategory(request.getCategory());
        }
    }
}
