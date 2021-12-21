package com.spirent.drools.dto.kpi.request;

import com.spirent.drools.dto.KpiAbstract;
import com.spirent.drools.dto.kpi.KpiLatency;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ysavi2
 * @since 20.12.2021
 */
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KpiRequest extends KpiAbstract {
    @NotNull
    @Schema(description = "List of the kpis to be validated.")
    private List<KpiLatency> kpis;
}
