package com.spirent.drools.config.rulesengine;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(3)
public class DroolsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() {
        return getKieServices().newKieFileSystem();
    }

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() {
        final KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);

        KieFileSystem kieFileSystem = kieFileSystem();
        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();

        return getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() {
        return kieContainer().newKieSession();
    }

    @Bean
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }
}
