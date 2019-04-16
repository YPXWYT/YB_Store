package com.tna.yb_store.service;

import com.tna.yb_store.entity.Order;
import com.tna.yb_store.entity.Product;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    List<Product> findProductAll();

    void updateProduct(Product product);

    void deleteProductById(int id);

    void deleteProductAll();
}
