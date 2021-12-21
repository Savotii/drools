package com.spirent.drools.rest.crud;

import com.spirent.drools.dto.rules.Rule;
import com.spirent.drools.service.rule.impl.RulesServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ysavi2
 * @since 01.12.2021
 */
@RequestMapping("/rules")
@Controller
@RequiredArgsConstructor
@Slf4j
public class RulesController {

    @Resource
    private RulesServiceImpl rulesService;

    @Operation(description = "Get all rules")
    @ApiResponse(responseCode = "200")
    @GetMapping("/getAll")
    public ResponseEntity<List<Rule>> getAllRules() {
        return ResponseEntity.ok(rulesService.getAllRules());
    }

    @Operation(description = "addRule")
    @ApiResponse(responseCode = "200")
    @PostMapping("/add")
    public ResponseEntity<Rule> addRule(@RequestBody @Validated Rule rule) {
        return ResponseEntity.ok(rulesService.saveRule(rule));
    }

    @Operation(description = "Update rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "403")
    })
    @PutMapping("/update")
    public ResponseEntity<Void> updateRule(@RequestBody @Validated Rule rule) {
        rulesService.updateRule(rule);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Delete the rule")
    @ApiResponses({
            @ApiResponse(description = "Rule has been deleted", responseCode = "204")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("id") long id) {
        rulesService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}
