package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.enams.ResultCode;
import com.jdbc.hacaton1.enams.ResultCodeAPI;
import com.jdbc.hacaton1.models.MineProducts;
import com.jdbc.hacaton1.models.ProductWithSeller;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.ProductsModel;
import com.jdbc.hacaton1.models.responseMessageAPI.ResponseMessageAPI;
import com.jdbc.hacaton1.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("products/")

public class ProductsController {

   private final ProductsService service;

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of ProductsFeed returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There is no any products",
                    content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductsFeed.class)))})
    })
    @Operation(summary = "This road returns products feed")
   @GetMapping("getFeed")
    public ResponseMessageAPI<List<ProductsFeed>> getProductsFeed(){
       try {
           return new ResponseMessageAPI<>(
                   service.getProductsFeed(),
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
                    description = "Product with special id returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There is no any products with this id",
                    content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductWithSeller.class)))})
    })
    @Operation(summary = "This road returns product with special id")
   @GetMapping("getById/{id}")
    public ResponseMessageAPI<ProductWithSeller> getProductById(@PathVariable Integer id){
       try {
           return new ResponseMessageAPI<>(
                   service.getProductById(id),
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
                    description = "Product is created successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Failed to add product to database",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class))})
    })
    @Operation(summary = "This road creates product")
   @PostMapping("create")
   public ResponseMessageAPI<Integer> createProduct(@RequestBody ProductsModel product){
       try {
           return new ResponseMessageAPI<>(
                   service.createProduct(product),
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
                    description = "List of users product is returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "User doesn't have any products",
                    content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductsModel.class)))})
    })
    @Operation(summary = "This road returns list of users products")
   @GetMapping("getByUserId/{id}")
   public ResponseMessageAPI<List<ProductsModel>> getProductsByUserId(@PathVariable Integer id){
       try {
           return new ResponseMessageAPI<>(
                   service.getProductsByUserId(id),
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
                    description = "All products without users evaluations returned successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any products without evaluation",
                    content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductsFeed.class)))})
    })
    @Operation(summary = "This road returns all products without users evaluate")
   @GetMapping("getAllWithoutEvaluation/{userId}")
    public ResponseMessageAPI<List<ProductsFeed>> getAllProductsWithoutEvaluation(@PathVariable Integer userId){
       try {
           return new ResponseMessageAPI<>(
                   service.getAllProductsWithoutEvaluation(userId),
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
                    description = "All YOURs products are returned successfully",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "You don't have any products yet",
                    content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MineProducts.class)))})
    })
    @Operation(summary = "This road returns all YOURs products by your id")
   @GetMapping("getAllMine/{userId}")
    public ResponseMessageAPI<List<MineProducts>> getAllMineProducts(@PathVariable Integer userId){
       try {
           return new ResponseMessageAPI<>(
                   service.getAllMineProducts(userId),
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
                    description = "Product without users evaluation returned successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There is not any product without evaluation",
                    content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductsFeed.class)))})
    })
    @Operation(summary = "This road returns one random product without users evaluate")
   @GetMapping("getWithoutEvaluationRandomly/{userId}")
   public ResponseMessageAPI<ProductsFeed> getProductWithoutEvaluationRandomly(@PathVariable Integer userId){
       try {
           return new ResponseMessageAPI<>(
                   service.getProductWithoutEvaluationRandomly(userId),
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
                    description = "Product updated successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There are not any products with this id",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))})
    })
    @Operation(summary = "This road updates product by special id")
   @PutMapping("updateById")
   public ResponseMessageAPI<String> updateProductById(@RequestParam Integer id, @RequestBody ProductsModel product){
       try {
           return new ResponseMessageAPI<>(
                   service.updateProductById(id, product),
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
                    description = "Products deleted successfully ",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "404",
                    description = "There isn't any products with this id",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))})
    })
    @Operation(summary = "This road deletes product by special id")
   @DeleteMapping("delete")
   public ResponseMessageAPI<String> deleteProductById(@RequestParam Integer id){
       try {
           return new ResponseMessageAPI<>(
                   service.deleteProductById(id),
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
