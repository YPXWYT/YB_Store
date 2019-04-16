package com.tna.yb_store.service.Impl;

import com.tna.yb_store.entity.Product;
import com.tna.yb_store.mapper.ProductMapper;
import com.tna.yb_store.service.ProductService;
import com.tna.yb_store.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional /*表示在该类下所有的方法都受事务控制*/
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    private RedisUtil redisUtil = new RedisUtil();

    public RedisUtil getRedisUtil() {
        return redisUtil;
    }

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public void addProduct(Product product) {
        this.productMapper.addProduct(product);
    }

    @Override
    public List<Product> findProductAll() {
        return productMapper.findProductAll();
    }

    @Override
    public void updateProduct(Product product) {
        this.productMapper.updateProduct(product);
    }

    @Override
    public void deleteProductById(int id) {
        this.productMapper.deleteProductById(id);
    }

    @Override
    public void deleteProductAll() {
        this.productMapper.deleteProductAll();

    }

}
