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
@RequestMapping("products/")

public class ProductsController {

   private final ProductsService service;

   @GetMapping("getFeed")
    public List<ProductsFeed> getProductsFeed(){
       return service.getProductsFeed();
   }

   @GetMapping("getById/{id}")
    public ProductWithSeller getProductById(@PathVariable Integer id){
       return service.getProductById(id);
   }

   @PostMapping("create")
   public Integer createProduct(@RequestBody ProductsModel product){
       return service.createProduct(product);
   }
   @GetMapping("getByUserId/{id}")
   public List<ProductsModel> getProductByUserId(@PathVariable Integer id){
       return service.getProductByUserId(id);
   }

   @GetMapping("getAllWithoutEvaluation/{userId}")
    public List<ProductsFeed> getAllProductsWithoutEvaluation(@PathVariable Integer userId){
       return service.getAllProductsWithoutEvaluation(userId);
   }

   @GetMapping("getAllMine/{userID}")
    public List<MineProducts> getAllMineProducts(@PathVariable Integer userID){
       return service.getAllMineProducts(userID);
   }
   @GetMapping("getWithoutEvaluationRandomly/{userId}")
   public ProductsFeed getProductWithoutEvaluationRandomly(@PathVariable Integer userId){
       return service.getProductWithoutEvaluationRandomly(userId);
   }

   @PutMapping("updateById")
   public String updateProductById(@RequestParam Integer id, @RequestBody ProductsModel product){
       return service.updateProductById(id, product);
   }

   @DeleteMapping("delete")
   public String deleteProductById(@RequestParam Integer id){
       return service.deleteProductById(id);
   }

}
