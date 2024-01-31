package com.jdbc.hacaton1.services;

import com.jdbc.hacaton1.dao.ProductsDao;
import com.jdbc.hacaton1.models.ProductWithSeller;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.ProductsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsDao dao;

    public List<ProductsFeed> getProductsFeed(){
        return dao.getProductsFeed();
    }

    public ProductWithSeller getProductById(Integer id){
        return dao.getProductById(id);
    }

    public Integer createProduct(ProductsModel product){
        return dao.createProduct(product);
    }

    public List<ProductsModel> getProductByUserId(Integer id){
        return dao.getProductByUserId(id);
    }
}
