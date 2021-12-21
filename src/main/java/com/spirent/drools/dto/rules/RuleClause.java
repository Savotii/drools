package com.spirent.drools.dto.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ysavi2
 * @since 08.12.2021
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class RuleClause {
    @Schema(description = "Name of the rule among the rule file(must be unique).", example = "Test rule.")
    private String ruleName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Salience of the rule (e.g. order)", example = "Positive or negative integer")
    private Long salience;

    @Schema(description = "When clause describes the behaviour that eval to true and pass to the Then clause.",
            example = "kpi: Kpi(latency > 7 && phase == KpiPhase.FIRST)")
    private String whenClause;

    @Schema(description = "Then clause describes the behaviour what should be done after when is true.",
            example = "kpi.setFailed(true);")
    private String thenClause;
}
