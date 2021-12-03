package com.spirent.drools.converter.impl;

import com.spirent.drools.converter.RuleContentConverter;
import com.spirent.drools.dto.rules.RuleContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

import static org.drools.compiler.lang.DroolsSoftKeywords.ATTRIBUTES;
import static org.drools.compiler.lang.DroolsSoftKeywords.END;
import static org.drools.compiler.lang.DroolsSoftKeywords.IMPORT;
import static org.drools.compiler.lang.DroolsSoftKeywords.PACKAGE;
import static org.drools.compiler.lang.DroolsSoftKeywords.RULE;
import static org.drools.compiler.lang.DroolsSoftKeywords.THEN;
import static org.drools.compiler.lang.DroolsSoftKeywords.WHEN;

/**
 * @author ysavi2
 * @since 01.12.2021
 */
@Component
@Slf4j
public class RuleContentConverterImpl implements RuleContentConverter {
    private static final String LINE_SEPARATOR_SYMBOL = "_/_/";
    private static final String BREAK_LINE_SYMBOL = " \n ";
    public static final String QUOTE = "\"";
    public static final String SLASH = "\\";

    @Override
    //todo build some validation of the input var.
    public RuleContent convertFromString(String content) {
        if (content.isBlank()) {
            // todo throw the content empty exception
            return new RuleContent();
        }

        RuleContent convertedContent = new RuleContent();
        if (!content.contains(LINE_SEPARATOR_SYMBOL)) {
            //todo should be thought the approach of the deserializing.
            return convertedContent;
        }

        var splitted = content.split(LINE_SEPARATOR_SYMBOL);
        for(int i = 0; i < splitted.length -1; i++) {
            String block = clearFirstSpecialSymbols(splitted[i]);

            String[] splittedBlock = block.split(StringUtils.SPACE);
            String blockType = splittedBlock[0];
            switch (blockType) {
                case PACKAGE -> convertAndSetPackageStringToContent(convertedContent, block);
                case IMPORT -> convertAndSetImportStringToContent(convertedContent, block);
                case ATTRIBUTES -> convertAndSetAttributesStringToContent(convertedContent, block);
                case RULE -> convertAndSetRuleStringToContent(convertedContent, block);
                case WHEN -> convertAndSetWhenStringToContent(convertedContent, block);
                case THEN -> convertAndSetThenStringToContent(convertedContent, block);
                default -> System.out.println("Nothing to convert yet.");
            }
        }
        return convertedContent;
    }

    private String clearFirstSpecialSymbols(String s) {
        return s.replace(BREAK_LINE_SYMBOL, StringUtils.EMPTY);
    }

    @Override
    //todo build some validation of the input var
    public String convertToString(RuleContent content) {
        StringBuilder sb = new StringBuilder();
        fillPackage(sb, content.getHeaderPackage());
        fillImports(sb, content.getImports());
        fillAttributes(sb, content.getAttributes());
        fillRuleName(sb, content.getRuleName());
        fillWhenClause(sb, content.getWhenClause());
        fillThenClause(sb, content.getThenClause());
        fillEndBlock(sb);
        return sb.toString();
    }

    @Override
    public String clearSpecialSymbols(String content) {
        return content.replace(LINE_SEPARATOR_SYMBOL, StringUtils.SPACE)
                .replace( QUOTE + QUOTE, QUOTE);
    }

    // todo find out is this block is mandatory?
    private void fillPackage(StringBuilder sb, String rulePackage) {
        if (rulePackage.isBlank()) {
            return;
        }

        //todo should be validated.
        sb.append(PACKAGE).append(StringUtils.SPACE).append(rulePackage).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL);
    }

    private void convertAndSetPackageStringToContent(RuleContent convertedContent, String value) {
        convertedContent.setHeaderPackage(value.replace(PACKAGE, StringUtils.EMPTY).trim());
    }

    private void convertAndSetImportStringToContent(RuleContent convertedContent, String value) {
        convertedContent.getImports().add(value.replace(IMPORT, StringUtils.EMPTY).trim());
    }

    private void convertAndSetAttributesStringToContent(RuleContent convertedContent, String value) {
        convertedContent.getAttributes().add(value.replace(ATTRIBUTES, StringUtils.EMPTY).trim());
    }

    private void convertAndSetRuleStringToContent(RuleContent convertedContent, String value) {
        convertedContent.setRuleName(value.replace(RULE, StringUtils.EMPTY).replace(QUOTE, QUOTE).trim());
    }

    private void convertAndSetWhenStringToContent(RuleContent convertedContent, String value) {
        convertedContent.setWhenClause(value.replace(WHEN, StringUtils.EMPTY).trim());
    }

    private void convertAndSetThenStringToContent(RuleContent convertedContent, String value) {
        convertedContent.setThenClause(value.replace(THEN, StringUtils.EMPTY).trim());
    }

    // todo find out is this block is mandatory?
    private void fillImports(StringBuilder sb, Set<String> ruleImports) {
        if (ruleImports.isEmpty()) {
            return;
        }

        //todo should be validated.
        ruleImports.forEach(i -> sb.append(IMPORT).append(StringUtils.SPACE).append(i).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL));
    }

    //todo
    private void fillAttributes(StringBuilder sb, Set<String> ruleAttributes) {

    }

    // todo find out is this block is mandatory?
    private void fillRuleName(StringBuilder sb, String ruleName) {
        if (ruleName.isBlank()) {
            return;
        }

        sb.append(BREAK_LINE_SYMBOL).append(RULE).append(StringUtils.SPACE).append(wrapByQuotes(ruleName)).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL);
    }

    private String wrapByQuotes(String ruleName) {
        return QUOTE + ruleName + QUOTE;
    }


    private void fillWhenClause(StringBuilder sb, String whenClause) {
        sb.append(WHEN).append(StringUtils.SPACE).append(whenClause).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL);
    }

    private void fillThenClause(StringBuilder sb, String thenClause) {
        sb.append(THEN).append(StringUtils.SPACE).append(thenClause).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL);
    }

    private void fillEndBlock(StringBuilder sb) {
        sb.append(END);
    }
}
