package com.tna.yb_store.controller;

import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Order;
import com.tna.yb_store.service.OrderService;
import com.tna.yb_store.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单API
 * POST    /v1/orders/insertOrder/{order}               新建一条订单
 * DELETE  /v1/orders/deleteOrderByOrderId/{order_id}   删除一条订单
 * DELETE  /v1/orders/deleteOrderByUserId/              删除该用户所有订单
 * PUT     /v1/orders/updateOrderByOrderId{order}       更新一条订单，提供全部信息
 * GET     /v1/orders/selectOrderAll                      所有订单(管理员接口)
 * GET    /v1/orders/selectOrderByUserId/              单个用户所有订单
 * GET     /v1/orders/selectOrderByOrderId/{order_id}     通过订单号查询订单
 */

@RequestMapping("/v1/orders")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {


    private final OrderService orderService;
    private final RedisUtil redisUtil;

    @Autowired
    public OrderController(OrderService orderService, RedisUtil redisUtil) {
        this.orderService = orderService;
        this.redisUtil = redisUtil;
    }

    @PostMapping("/insertOrder")
    public HttpEntity<BaseResponseBody> insertOrder(
            @RequestHeader(name = "X-Token") String user_id, Order order) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return orderService.insertOrder(order);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrderByOrderId")
    public HttpEntity<BaseResponseBody> deleteOrderByOrderId(
            @RequestHeader(name = "X-Token") String user_id, int order_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return orderService.deleteOrderByOrderId(order_id);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrderByUserId")
    public HttpEntity<BaseResponseBody> deleteOrderByUserId(
            @RequestHeader(name = "X-Token") String user_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return orderService.deleteOrderByUserId(user_id);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @PutMapping("/updateOrderByOrderId")
    public HttpEntity<BaseResponseBody> updateOrderByOrderId(
            @RequestHeader(name = "X-Token") String user_id, Order order) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return orderService.updateOrder(order);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @GetMapping("/selectOrderByUserId")
    public HttpEntity<BaseResponseBody> selectOrderByUserId(
            @RequestHeader(name = "X-Token") String user_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return orderService.selectOrderByUserId(user_id);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
            return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
        }
    }


    @GetMapping("/selectOrderByOrderId")
    public HttpEntity<?> selectOrderByOrderId(@RequestHeader(name = "X-Token") String user_id, int order_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (redisUtil.checkToken(user_id)) {
            return orderService.selectOrderByOrderId(order_id);
        } else {
            baseResponseBody.setCode(-1);
            baseResponseBody.setMsg("你还没有授权噢！");
            return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
        }
    }
}
