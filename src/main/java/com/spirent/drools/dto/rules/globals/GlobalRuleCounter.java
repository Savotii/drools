package com.spirent.drools.dto.rules.globals;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ysavi2
 * @since 09.12.2021
 */
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class GlobalRuleCounter {
    private static final Map<String, Integer> counter = new LinkedHashMap<>();

    public Integer get(String key) {
        Integer result = counter.getOrDefault(key, 0);
        log.info("Get {} result {} ", key, result);
        return result;
    }

    public void update(String key) {
        log.info(" Update {} ", key);
        counter.compute(key, (k, v) -> (v == null) ? 1 : ++v);
    }

    public Integer updateAndGet(String key) {
        var result = counter.compute(key, (k, v) -> (v == null) ? 1 : ++v);
        log.info("UpdateAndGet {} result {}", key, result);
        return result;
    }

    public boolean checkClause(String key) {
        boolean result = updateAndGet(key) % 2 == 0;
        log.info("CheckClause {} result {}", key, result);
        return result;
    }
}
