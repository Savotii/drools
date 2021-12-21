package com.spirent.drools.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ysavi2
 * @since 20.12.2021
 */

@Getter
@Setter
public abstract class KpiAbstract {
    @Schema(description = "Agent id.", example = "5Fsg54-09dd-8395-123a-dre3ca098084")
    @JsonProperty("agentId")
    protected String agentId;
    @Schema(description = "Agent test name", example = "N2N3SIPRegistration")
    @JsonProperty("agentTestName")
    protected String agentTestName;
    @Schema(description = "Test session id", example = "748ad659-307f-458c-a62e-4feacc45e1bd")
    @JsonProperty("testSessionId")
    protected String testSessionId;
    @Schema(description = "Test id", example = "7b659ea5-6110-4c16-b25f-b7b128867457")
    @JsonProperty("testId")
    protected String testId;
    @Schema(description = "Agent test id", example = "89bb7e5e-5153-11ec-bf63-0242ac130002")
    @JsonProperty("agentTestId")
    protected String agentTestId;
    @Schema(description = "Workflow id", example = "cb6f234a-dec3-4374-87e7-baf44c16e2ef")
    @JsonProperty("workflowId")
    protected String workflowId;
    @Schema(description = "Overlay id", example = "0e0c6dda-95bd-4720-898a-ffe5ca819338")
    @JsonProperty("overlayId")
    protected String overlayId;

    //todo we do not know where is it from. by default will be equal to testId
    @Schema(description = "Network element id", example = "f274b4d1-915e-4878-908a-051151c4b6b3")
    @JsonProperty("networkElementId")
    protected String networkElementId;

    @Schema(description = "Alert category", example = "VOICE")
    @JsonProperty("category")
    protected String category;
    @Schema(description = "Package", example = "5G Core - N2N3 Assurance")

    @JsonProperty("package")
    protected String pkg;
    @Schema(description = "Test name", example = "Registration")
    @JsonProperty("testName")
    protected String testName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected Boolean failed;
}
