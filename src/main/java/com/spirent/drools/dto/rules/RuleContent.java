package com.spirent.drools.dto.rules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.drools.modelcompiler.builder.generator.RuleContext;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author ysavi2
 * @since 01.12.2021
 * <p>
 * This class represents the content part of the {@link Rule}
 * <p>
 * **** Pattern
 * package;
 * imports;
 * global;
 * attributes;
 * rulename;
 * when clause;
 * then clause;
 * end;
 * <p>
 * end pattern ***
 */
@Data
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
public class RuleContent {

    @Schema(description = "Block depicts the package.",
            example = "rules or com.spirent.rules")
    private String headerPackage;

    @Schema(description = "Block depicts the import states for including special classes for evaluating the conditions. Maybe an array of the imports.",
            example = "com.spirent.drools.dto.kpi.Kpi;",
            type = "array")
    private Set<String> imports = new LinkedHashSet<>();

//    global Integer count;
//
//    rule "Initialize"
//    salience 100
//    when
//            then
//       kcontext.getKieRuntime().setGlobal("count", 1);
//    end
    @Schema(description = "Block depicts the global variables which may be used during the rules execution(e.g. the rule may be dependent on previous rule)",
            type = "array",
            example = "java.lang.Integer delayInSeconds;")
    private List<String> globalVariables = new LinkedList<>();

    @Schema(description = "Block depicts the additional attributes.",
            example = "no-loop",
            type = "array")
    private Set<String> attributes = new LinkedHashSet<>();

    private RuleContext.RuleDialect dialect = RuleContext.RuleDialect.JAVA;

    @Schema(description = "Clauses which may be defined in order.")
    private List<RuleClause> clauses = new LinkedList<>();
}
