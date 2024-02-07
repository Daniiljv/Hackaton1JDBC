package com.jdbc.hacaton1.dao;


import com.jdbc.hacaton1.models.MineProducts;
import com.jdbc.hacaton1.models.ProductWithSeller;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.ProductsModel;

import java.util.List;

public interface ProductsDao {

    List<ProductsFeed> getProductsFeed();

    ProductWithSeller getProductById(Integer id);

    Integer createProduct(ProductsModel product);

    List<ProductsModel> getProductByUserId(Integer id);

    List<ProductsFeed> getAllProductsWithoutEvaluation(Integer userId);

    List<MineProducts> getAllMineProducts(Integer userID);

    ProductsFeed getProductWithoutEvaluationRandomly(Integer userId);

    String updateProductById(Integer id, ProductsModel product);

    String deleteProductById(Integer id);

}
