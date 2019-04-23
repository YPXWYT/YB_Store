package com.tna.yb_store.controller;

import com.tna.yb_store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rest 风格 api
 *
 * GET     /api/v1/orders        所有订单
 * GET     /api/v1/orders/{userId}       单个用户所有订单
 * GET     /api/v1/orders/{id}   获取一条订单
 * POST    /api/v1/orders        新建一条订单
 * PUT     /api/v1/orders/{id}   更新一条订单，提供全部信息
 * PATCH   /api/v1/orders/{id}   更新一条订单，提供部分信息
 * DELETE  /api/v1/orders/{id}   删除一条订单
 * DELETE  /API/v1/orders        删除所有订单
 *
 */

@RequestMapping("/v1")
@RestController
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    /**
     * 获取所有书用户订单
     * GET     /api/v1/orders        所有用户订单
     *
     * @return http 响应
     */
    @GetMapping("/orders")
    public HttpEntity<?> orders() {
        return new ResponseEntity<>(orderService.findOrderAll(), HttpStatus.OK);
    }

    /**
     * 获取一个用户 * GET     /api/v1/orders/{id}   获取一个用户
     * @param id id
     * @return http 响应
     */  @GetMapping("/orders/{id}")
    public HttpEntity<?> ordersOne(@PathVariable String id) {
        return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
    }
}
