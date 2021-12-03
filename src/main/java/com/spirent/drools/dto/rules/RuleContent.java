package com.spirent.drools.dto.rules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
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
    private Set<String> imports = new HashSet<>();
    @Schema(description = "Block depicts the additional attributes.",
            example = "no-loop",
            type = "array")
    private Set<String> attributes = new HashSet<>();
    @Schema(description = "Name of the rule, must be taken from parent class.", example = "Test rule.")
    private String ruleName;
    @Schema(description = "When clause describes the behaviour that eval to true and pass to the Then clause.",
            example = "kpi: Kpi(latency > 7 && phase == KpiPhase.FIRST)")
    private String whenClause;
    @Schema(description = "Then clause describes the behaviour what should be done after when is true.",
            example = "kpi.setFailed(true);")
    private String thenClause;

    public static class RuleContentBuilder {
        private String headerPackage;
        private Set<String> imports = new HashSet<>();
        private Set<String> attributes = new HashSet<>();
        private String whenClause;
        private String ruleName;
        private String thenClause;

        public RuleContentBuilder withPackage(String headerPackage) {
            this.headerPackage = headerPackage;
            return this;
        }

        public RuleContentBuilder withImport(String oneImport) {
            this.imports.add(oneImport);
            return this;
        }

        public RuleContentBuilder withImports(Set<String> imports) {
            this.imports.addAll(imports);
            return this;
        }

        public RuleContentBuilder withAttributes(Set<String> attributes) {
            this.attributes = attributes;
            return this;
        }

        public RuleContentBuilder withRuleName(String ruleName) {
            this.ruleName = ruleName;
            return this;
        }

        public RuleContentBuilder withWhenClause(String whenClause) {
            this.whenClause = whenClause;
            return this;
        }

        public RuleContentBuilder withThenClause(String thenClause) {
            this.thenClause = thenClause;
            return this;
        }

        public RuleContent build() {
            return new RuleContent()
                    .setHeaderPackage(this.headerPackage)
                    .setImports(this.imports)
                    .setAttributes(this.attributes)
                    .setRuleName(this.ruleName)
                    .setWhenClause(this.whenClause)
                    .setThenClause(this.thenClause);
        }
    }
}
