package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.enams.ResultCode;
import com.jdbc.hacaton1.enams.ResultCodeAPI;
import com.jdbc.hacaton1.models.EvaluationModel;
import com.jdbc.hacaton1.models.responseMessageAPI.ResponseMessageAPI;
import com.jdbc.hacaton1.services.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("evaluations/")

public class EvaluationController {

    private final EvaluationService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Evaluation created successfully ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to add evaluation to database",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))})
    })
    @Operation(summary = "This road creates evaluate")
    @PostMapping("create")
    public ResponseMessageAPI<Integer> createEvaluation(@RequestBody EvaluationModel evaluation) {
        try {
            return new ResponseMessageAPI<>(
                    service.createEvaluation(evaluation),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (RuntimeException rte) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.FAIL,
                    rte.getClass().getSimpleName(),
                    rte.getMessage(),
                    ResultCode.FAIL.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Avg evaluation returned successfully ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Double.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "The product doesn't have any evaluations",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Double.class))})
    })
    @Operation(summary = "This road returns average evaluation by product id")
    @GetMapping("getAvgEvaluationByProductId/{id}")
    public ResponseMessageAPI<Double> getAvgEvaluation(@PathVariable Integer id) {
        try {
            return new ResponseMessageAPI<>(
                    service.getAvgEvaluation(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Evaluation deleted successfully ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There isn't any evaluation with this id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @Operation(summary = "This road deletes evaluation by id")
    @DeleteMapping("deleteById")
    public ResponseMessageAPI<String> deleteEvaluationById(@RequestParam Integer id) {
        try {
            return new ResponseMessageAPI<>(
                    service.deleteEvaluationById(id),
                    ResultCodeAPI.SUCCESS,
                    null,
                    "success",
                    ResultCode.SUCCESS.getHttpCode()
            );
        } catch (NullPointerException npe) {
            return new ResponseMessageAPI<>(
                    null,
                    ResultCodeAPI.NOT_FOUND,
                    npe.getClass().getSimpleName(),
                    npe.getMessage(),
                    ResultCode.NOT_FOUND.getHttpCode());
        }
    }
}
