package com.tna.yb_store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * rest 风格 api
 * <p>
 * GET     /api/v1/products        所有产品信息
 * GET     /api/v1/products/{id}   获取一条产品信息
 * POST    /api/v1/products        新建一条产品信息
 * PUT     /api/v1/products/{id}   更新一条产品信息，提供全部信息
 * PATCH   /api/v1/products/{id}   更新一条产品信息，提供部分信息
 * DELETE  /api/v1/products/{id}   删除一条产品信息
 * DELETE  /API/v1/products        删除所有产品信息
 */
@RestController
@RequestMapping("/v1")
public class ProductController {

}
