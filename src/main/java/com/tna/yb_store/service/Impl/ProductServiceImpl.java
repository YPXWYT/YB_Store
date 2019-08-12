package com.tna.yb_store.service.Impl;

import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Product;
import com.tna.yb_store.mapper.ProductMapper;
import com.tna.yb_store.service.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional /*表示在该类下所有的方法都受事务控制*/
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public HttpEntity<BaseResponseBody> insertProduct(Product product) {
        return null;
    }

    @Override
    public HttpEntity<BaseResponseBody> selectProductAll(int currIndex, int sizePerPage) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        BaseResponseBody baseResponseBody1;
        List<Product> list = productMapper.selectProductAll(currIndex, sizePerPage);
        baseResponseBody1 = BaseResponseBody.checkSelectAll(baseResponseBody, list);
        return new ResponseEntity<BaseResponseBody>(baseResponseBody1, HttpStatus.OK);

    }

    @Override
    public HttpEntity<BaseResponseBody> selectProductById(int product_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        BaseResponseBody baseResponseBody1;
        Product product = productMapper.selectProductById(product_id);
        List<Product> list = new ArrayList<Product>();
        list.add(product);
        baseResponseBody1 = BaseResponseBody.checkSelectAll(baseResponseBody, list);
        return new ResponseEntity<BaseResponseBody>(baseResponseBody1, HttpStatus.OK);
    }
}
