package com.spirent.drools.converter.impl;

import com.spirent.drools.converter.RuleContentConverter;
import com.spirent.drools.dto.rules.RuleClause;
import com.spirent.drools.dto.rules.RuleContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.drools.compiler.lang.DroolsSoftKeywords.ATTRIBUTES;
import static org.drools.compiler.lang.DroolsSoftKeywords.END;
import static org.drools.compiler.lang.DroolsSoftKeywords.GLOBAL;
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
        Map<String, RuleClause> clauses = new LinkedHashMap<>();
        if (!content.contains(LINE_SEPARATOR_SYMBOL)) {
            //todo should be thought the approach of the deserializing.
            return convertedContent;
        }

        String previousRuleName = StringUtils.EMPTY;
        var splitted = content.split(LINE_SEPARATOR_SYMBOL);
        for (int i = 0; i < splitted.length - 1; i++) {
            String block = clearFirstSpecialSymbols(splitted[i]);

            String[] splittedBlock = block.split(StringUtils.SPACE);
            String blockType = splittedBlock[0];
            switch (blockType) {
                case PACKAGE -> convertAndSetPackageStringToContent(convertedContent, block);
                case IMPORT -> convertAndSetImportStringToContent(convertedContent, block);
                case GLOBAL -> convertAndSetGlobalVariablesStringToContent(convertedContent, block);
                case ATTRIBUTES -> convertAndSetAttributesStringToContent(convertedContent, block);

                case RULE -> {
                    previousRuleName = getRuleNameFromPattern(block);
                    RuleClause clause = clauses.getOrDefault(previousRuleName, new RuleClause());
                    clause.setRuleName(previousRuleName);
                    clauses.putIfAbsent(previousRuleName, clause);
                }
                case WHEN -> convertAndSetWhenStringToContent(clauses.get(previousRuleName), block);
                case THEN -> convertAndSetThenStringToContent(clauses.get(previousRuleName), block);

                default -> log.info("Nothing to convert yet.");
            }
        }

        convertedContent.setClauses(new LinkedList<>(clauses.values()));
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
        fillGlobalVariables(sb, content.getGlobalVariables());
        fillAttributes(sb, content.getAttributes());
        fillClauses(sb, content.getClauses());
        return sb.toString();
    }

    @Override
    public String clearSpecialSymbols(String content) {
        return content.replace(LINE_SEPARATOR_SYMBOL, StringUtils.SPACE)
                .replace(QUOTE + QUOTE, QUOTE);
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

    private void convertAndSetGlobalVariablesStringToContent(RuleContent convertedContent, String value) {
        convertedContent.getGlobalVariables().add(value.replace(GLOBAL, StringUtils.EMPTY).trim());
    }

    private void convertAndSetImportStringToContent(RuleContent convertedContent, String value) {
        convertedContent.getImports().add(value.replace(IMPORT, StringUtils.EMPTY).trim());
    }

    private void convertAndSetAttributesStringToContent(RuleContent convertedContent, String value) {
        convertedContent.getAttributes().add(value.replace(ATTRIBUTES, StringUtils.EMPTY).trim());
    }

    private String getRuleNameFromPattern(String value) {
        return value.replace(RULE, StringUtils.EMPTY).replace(QUOTE, QUOTE).trim();
    }

    private void convertAndSetWhenStringToContent(RuleClause clause, String value) {
        clause.setWhenClause(value.replace(WHEN, StringUtils.EMPTY).trim());
    }

    private void convertAndSetThenStringToContent(RuleClause clause, String value) {
        clause.setThenClause(value.replace(THEN, StringUtils.EMPTY).trim());
    }

    // todo find out is this block is mandatory?
    private void fillImports(StringBuilder sb, Set<String> ruleImports) {
        if (ruleImports.isEmpty()) {
            return;
        }

        //todo should be validated.
        ruleImports.forEach(i -> sb.append(IMPORT).append(StringUtils.SPACE).append(i).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL));
    }

    private void fillGlobalVariables(StringBuilder sb, List<String> globalVariables) {
        if (globalVariables.isEmpty()) {
            return;
        }

        //todo should be validated.
        globalVariables.forEach(i -> sb.append(GLOBAL).append(StringUtils.SPACE).append(i).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL));
    }

    //todo
    private void fillAttributes(StringBuilder sb, Set<String> ruleAttributes) {
        //stub
    }

    private void fillClauses(StringBuilder sb, List<RuleClause> clauses) {
        if (clauses.isEmpty()) {
            return;
        }

        /* Example.
         * rule "Name of your rule"
         * when
         *   instance_of_the_class : Class(filed == "something")
         *   another_instance_of_the_class : AnotherClass();
         * then
         *   instance_of_the_class.setSomething(true/false/other values);
         *   another_instance_of_the_class.setSomething(true/false/other values);
         * end
         */
        clauses.forEach(cl -> {
            fillRuleName(sb, cl.getRuleName());
            fillWhenClause(sb, cl.getWhenClause());
            fillThenClause(sb, cl.getThenClause());
            fillEndBlock(sb);
        });
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
        sb.append(END).append(StringUtils.SPACE).append(LINE_SEPARATOR_SYMBOL).append(BREAK_LINE_SYMBOL);
    }
}
