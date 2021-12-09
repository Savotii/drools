package com.spirent.drools.dto.rules.global;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ysavi2
 * @since 09.12.2021
 */
@Getter
@Setter
@NoArgsConstructor
public class GlobalRuleCounter {
    private static final Map<String, Integer> counter = new LinkedHashMap<>();

    public Integer get(String key) {
        return counter.getOrDefault(key, 0);
    }

    public void update(String key) {
        counter.compute(key, (k, v) -> (v == null) ? 1 : ++v);
    }

    public Integer updateAndGet(String key) {
        var result = counter.compute(key, (k, v) -> (v == null) ? 1 : ++v);
        System.out.println(result);
        return result;
    }

    public boolean checkClause(String key) {
        return updateAndGet(key) % 2 == 0;
    }
}
