package com.tna.yb_store.service;

import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Order;
import com.tna.yb_store.entity.Product;
import org.springframework.http.HttpEntity;

import java.util.List;

public interface ProductService {

    HttpEntity<BaseResponseBody> insertProduct(Product product);

    HttpEntity<BaseResponseBody> selectProductAll(int currIndex, int sizePerPage);

    HttpEntity<BaseResponseBody> selectProductById(int product_id);
}
