package com.spirent.drools.dto.kpi;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Kpi {
    @Schema(description = "Latency.")
    @NotNull
    private Double latency;

    @Schema(description = "Phase.")
    @NotNull
    private KpiPhase phase;

    @Schema(description = "Location.")
    @NotNull
    private Location location;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean failed;

    @Schema(description = "Request time. Timestamp")
    @NotNull
    private Long timestamp;
}
