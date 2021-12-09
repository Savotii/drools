package com.spirent.drools.dto.rules;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author ysavi2
 * @since 01.12.2021
 */
@Data
@Accessors(chain = true)
@Schema(description = "Rule", required = true)
public class Rule {


    @Parameter(name = "id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "Id of the rule object, uses when the rule must be changed.", example = "1")
    private Long id;

    @NotNull
    @JsonProperty(value = "name")
    @Schema(description = "Rule name", example = "Test rule")
    private String name;

    @JsonProperty(value = "content_object")
    @NotNull
    @Schema(description = "Content includes the necessary structure of the rule's description.")
    private RuleContent ruleContent;

    @JsonProperty("version")
    @Schema(description = "version", example = "1")
    @NotNull
    private String version;

    @Schema(description = "Last modified time", example = "2021-12-02T13:16:01.914637Z")
    @JsonProperty(value = "Last modified time")
    private String lastModifyTime;

    @Schema(description = "Creation time", example = "2021-12-02T13:16:01.914637Z")
    @JsonProperty(value = "Creation time")
    @NotNull
    private String createTime;
}
