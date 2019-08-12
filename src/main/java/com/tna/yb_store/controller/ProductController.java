package com.tna.yb_store.controller;

import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Product;
import com.tna.yb_store.service.ProductService;
import com.tna.yb_store.utils.redis.RedisUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 产品API
 * GET     /v1/products/selectProductAll/{page,sizePerPage}      所有产品信息
 * GET     /v1/products/selectProductById/{product_id}   获取一条产品信息
 * POST    /v1/products        新建一条产品信息
 * PUT     /v1/products/{id}   更新一条产品信息，提供全部信息
 * PATCH   /v1/products/{id}   更新一条产品信息，提供部分信息
 * DELETE  /v1/products/{id}   删除一条产品信息
 * DELETE  /API/v1/products        删除所有产品信息
 */
@RestController
@RequestMapping("/v1/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final RedisUtil redisUtil;
    private final ProductService productService;

    public ProductController(RedisUtil redisUtil, ProductService productService) {
        this.redisUtil = redisUtil;
        this.productService = productService;
    }

    @GetMapping("/selectProductAll")
    public HttpEntity<BaseResponseBody> selectProductAll(
            @RequestHeader(name = "X-Token") String user_id, int page, int sizePerPage) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        BaseResponseBody baseResponseBody1;
        if (redisUtil.checkToken(user_id)) {
            int currIndex = (page - 1) * sizePerPage;
            return productService.selectProductAll(currIndex, sizePerPage);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("您还未授权！");
            return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
        }
    }

    @GetMapping("/selectProductById")
    public HttpEntity<BaseResponseBody> selectProductById(@RequestHeader(name = "X-Token") String user_id, int product_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return productService.selectProductById(product_id);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("您还未授权！");
            return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
        }
    }
}
