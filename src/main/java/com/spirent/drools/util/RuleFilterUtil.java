package com.spirent.drools.util;

import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.dto.rules.filter.KpiThresholdFilter;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Objects;

/**
 * @author ysavi2
 * @since 22.12.2021
 */
@UtilityClass
public class RuleFilterUtil {

    public static void populateThreshold(KpiRequest request, List<KpiThresholdFilter> filters) {
        var basedThreshold = filters.stream().filter(thr -> thr.overlayId() == null && thr.testId() == null).findFirst();
        var dedicatedThreshold = filters.stream().filter(thr -> Objects.equals(request.getTestId(), thr.testId()) && Objects.equals(request.getOverlayId(), thr.overlayId())).findFirst();

        request.getKpis().forEach(kpi -> kpi.setThreshold(dedicatedThreshold.orElseGet(basedThreshold::get).value()));
    }
}
