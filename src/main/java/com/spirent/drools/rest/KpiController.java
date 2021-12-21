package com.spirent.drools.rest;

import com.spirent.drools.dto.alert.AlertEvent;
import com.spirent.drools.dto.kpi.Kpi;
import com.spirent.drools.dto.kpi.request.KpiRequest;
import com.spirent.drools.service.kpi.KpiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ysavi2
 * @since 02.12.2021
 */
@RequestMapping("/kpi")
@RestController
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;
    private final Validator validator;

    @Operation(description = "Validate the provided kpi by Rule engine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/validate")
    public ResponseEntity<Kpi> validate(@RequestBody Kpi kpiRequest) {
        Set<ConstraintViolation<Kpi>> validated = validator.validate(kpiRequest);
        if (!validated.isEmpty()) {
            throw new ValidationException(
                    validated.stream()
                            .map(v -> String.format("Field %s - %s. %n ", v.getPropertyPath(), v.getMessage()))
                            .collect(Collectors.joining())
            );
        }
        return ResponseEntity.ok(kpiService.validateRules(kpiRequest));
    }

    @Operation(description = "Validate the provided kpi by Rule engine.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/validateKpi")
    public ResponseEntity<AlertEvent> validateKpi(@RequestBody KpiRequest kpiRequest) {
        Set<ConstraintViolation<KpiRequest>> validated = validator.validate(kpiRequest);
        if (!validated.isEmpty()) {
            throw new ValidationException(
                    validated.stream()
                            .map(v -> String.format("Field %s - %s. %n ", v.getPropertyPath(), v.getMessage()))
                            .collect(Collectors.joining())
            );
        }
        return ResponseEntity.ok(kpiService.validateRules(kpiRequest));
    }
}
