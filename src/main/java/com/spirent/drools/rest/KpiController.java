package com.spirent.drools.rest;

import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.service.KpiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@RequestMapping("/kpi")
@Controller
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    @Operation(description = "Validate the provided kpi by Rule engine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/validate")
    public ResponseEntity<Kpi> validate(@RequestBody @Validated Kpi kpiRequest) {
        return ResponseEntity.ok(kpiService.validateRules(kpiRequest));
    }
}
