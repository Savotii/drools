package com.spirent.drools.converter;

import com.spirent.drools.dto.rules.RuleContent;

/**
 * @author ysavi2
 * @since 01.12.2021
 *
 * Provides all necessary methods with converting whether to string or from to content.
 *
 */
public interface RuleContentConverter {
    RuleContent convertFromString(String content);

    String convertToString(RuleContent content);

    String clearSpecialSymbols(String content);
}
