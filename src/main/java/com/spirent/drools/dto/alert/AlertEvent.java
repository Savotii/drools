package com.spirent.drools.dto.alert;

import com.spirent.drools.dto.KpiAbstract;
import com.spirent.drools.dto.kpi.alert.FailedKpi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;

/**
 * @author ysavi2
 * @since 16.12.2021
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AlertEvent extends KpiAbstract {
    @Schema(description = "Date of the raised incident.", example = "2021-12-10T13:12:10Z")
    private Instant timestamp = Instant.now();
    @Schema(description = "")
    private String id;
    @Schema(description = "Rule which has been failed.")
    private String name = "High latency alert";
    @Schema(description = "alert type", example = "CRITICAL, WARN etc.")
    private AlertLevel level;
    @Schema(description = "List of the failed kpis", type = "array", example = "{\n" +
            "      \"id\": \"hostLatency\",\n" +
            "      \"value\": 34534,\n" +
            "      \"threshold\" : 1000\n" +
            "    }")
    private List<FailedKpi> failedKpis;
}