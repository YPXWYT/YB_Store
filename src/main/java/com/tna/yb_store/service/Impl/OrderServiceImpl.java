package com.tna.yb_store.service.Impl;

import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Order;
import com.tna.yb_store.entity.OrderAndProduct;
import com.tna.yb_store.entity.Product;
import com.tna.yb_store.mapper.OrderMapper;
import com.tna.yb_store.mapper.ProductMapper;
import com.tna.yb_store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional /*表示在该类下所有的方法都受事务控制*/
public class OrderServiceImpl implements OrderService {

    private final OrderMapper ordersMapper;
    private final ProductMapper productMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper ordersMapper, ProductMapper productMapper) {
        this.ordersMapper = ordersMapper;
        this.productMapper = productMapper;
    }

    @Override
    public HttpEntity<BaseResponseBody> insertOrder(Order order) {
        int flag = this.ordersMapper.insertOrder(order);
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (flag > 0) {
            baseResponseBody.setCode(0);
            baseResponseBody.setMsg("插入订单成功！");
        } else {
            baseResponseBody.setCode(2);
            baseResponseBody.setMsg("数据库错误！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @Override
    public HttpEntity<BaseResponseBody> deleteOrderByUserId(String user_id) {
        int flag = ordersMapper.deleteOrderByUserId(user_id);
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        if (flag > 0) {
            baseResponseBody.setCode(0);
            baseResponseBody.setMsg("删除订单成功！");
        } else {
            baseResponseBody.setCode(2);
            baseResponseBody.setMsg("数据库错误或者没有该用户订单！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    @Override
//    @CacheEvict(cacheNames = "delOrder", allEntries = true, beforeInvocation = true, condition = "#id>0")
    public HttpEntity<BaseResponseBody> deleteOrderByOrderId(int id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        int flag = ordersMapper.deleteOrderByOrderId(id);
        if (flag > 0) {
            baseResponseBody.setCode(0);
            baseResponseBody.setMsg("删除订单成功！");
        } else {
            baseResponseBody.setCode(2);
            baseResponseBody.setMsg("数据库错误或者没有该用户订单！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody,HttpStatus.OK);
    }

    @Override
//    @CachePut(cacheNames = "updateOrder", condition = "#order!=null", unless = "#result>0")
    public HttpEntity<BaseResponseBody> updateOrder(Order order) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        int flag = ordersMapper.updateOrder(order);
        if (flag > 0) {
            baseResponseBody.setCode(0);
            baseResponseBody.setMsg("更新订单成功！");
        } else {
            baseResponseBody.setCode(2);
            baseResponseBody.setMsg("数据库错误！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody,HttpStatus.OK);
    }


    @Override
//    @Cacheable(cacheNames = "findOrderById")
    public ResponseEntity<BaseResponseBody> selectOrderByOrderId(int id) {
        Order order = this.ordersMapper.selectOrderByOrderId(id);
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        BaseResponseBody baseResponseBody1;
        if (order != null) {
            Product product = productMapper.selectProductById(order.getProduct_id());
            List<OrderAndProduct> orderAndProducts = new ArrayList<OrderAndProduct>();
            orderAndProducts.add(assignment(product, order));

            baseResponseBody1 = BaseResponseBody.checkSelectAll(baseResponseBody, orderAndProducts);
            return new ResponseEntity<BaseResponseBody>(baseResponseBody1, HttpStatus.OK);
        } else {
            baseResponseBody.setCode(1);
            baseResponseBody.setMsg("查询失败！");
        }
        return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
    }

    private OrderAndProduct assignment(Product product, Order order) {
        OrderAndProduct orderAndProduct = new OrderAndProduct();
        orderAndProduct.setId(order.getId());
        orderAndProduct.setProduct(product);
        orderAndProduct.setYb_userid(order.getYb_userid());
        orderAndProduct.setYb_username(order.getYb_username());
        orderAndProduct.setYb_usernick(order.getYb_usernick());
        orderAndProduct.setYb_money(order.getYb_money());
        orderAndProduct.setIs_enable(order.isIs_enable());
        orderAndProduct.setStatus(order.getStatus());
        orderAndProduct.setCreate_user(order.getCreate_user());
        orderAndProduct.setModify_user(order.getModify_user());
        orderAndProduct.setYb_sex(order.getYb_sex());
        orderAndProduct.setCreate_time(order.getCreate_time());
        orderAndProduct.setModify_time(order.getModify_time());
        return orderAndProduct;
    }

    @Override
//    @Cacheable(cacheNames = "findOrderByUserId")
    public ResponseEntity<BaseResponseBody> selectOrderByUserId(String user_id) {
        BaseResponseBody baseResponseBody = BaseResponseBody.getInstance();
        List<Order> orders = ordersMapper.selectOrderByUserId(user_id);
        List<OrderAndProduct> orderAndProducts = new ArrayList<OrderAndProduct>();
        for (Order order : orders) {
            Product product = productMapper.selectProductById(order.getProduct_id());
            orderAndProducts.add(assignment(product, order));
        }
        return new ResponseEntity<BaseResponseBody>(BaseResponseBody.checkSelectAll(baseResponseBody, orderAndProducts), HttpStatus.OK);
    }

}
