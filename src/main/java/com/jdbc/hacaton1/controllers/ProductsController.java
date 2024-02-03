package com.jdbc.hacaton1.controllers;

import com.jdbc.hacaton1.models.MineProducts;
import com.jdbc.hacaton1.models.ProductWithSeller;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.ProductsModel;
import com.jdbc.hacaton1.services.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductsController {

   private final ProductsService service;

   @GetMapping("getProductsFeed")
    public List<ProductsFeed> getProductsFeed(){
       return service.getProductsFeed();
   }

   @GetMapping("getProductById:{id}")
    public ProductWithSeller getProductById(@PathVariable Integer id){
       return service.getProductById(id);
   }

   @PostMapping("createProduct")
   public Integer createProduct(@RequestBody ProductsModel product){
       return service.createProduct(product);
   }
   @GetMapping("getProductsByUserId:{id}")
   public List<ProductsModel> getProductByUserId(@PathVariable Integer id){
       return service.getProductByUserId(id);
   }

   @GetMapping("getAllProductsWithoutEvaluation:{userId}")
    public List<ProductsFeed> getAllProductsWithoutEvaluation(@PathVariable Integer userId){
       return service.getAllProductsWithoutEvaluation(userId);
   }

   @GetMapping("getAllMineProducts:{userID}")
    public List<MineProducts> getAllMineProducts(@PathVariable Integer userID){
       return service.getAllMineProducts(userID);
   }
   @GetMapping("getProductWithoutEvaluationRandomly:{userId}")
   public ProductsFeed getProductWithoutEvaluationRandomly(@PathVariable Integer userId){
       return service.getProductWithoutEvaluationRandomly(userId);
   }

}
