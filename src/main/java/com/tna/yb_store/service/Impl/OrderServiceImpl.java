package com.tna.yb_store.service.Impl;

import com.tna.yb_store.entity.Order;
import com.tna.yb_store.mapper.OrderMapper;
import com.tna.yb_store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional /*表示在该类下所有的方法都受事务控制*/
public class OrderServiceImpl implements OrderService {

    private final OrderMapper ordersMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper ordersMapper) {
        this.ordersMapper = ordersMapper;
    }

    @Override
    public void addOrder(Order order) {
        this.ordersMapper.addOrder(order);
    }

    @Override
    public List<Order> findOrderAll() {
        return ordersMapper.findOrderAll();
    }

    @Override
    @CachePut(cacheNames = "updateOrder", condition = "#order!=null", unless = "#result>0")
    public void updateOrder(Order order) {
        ordersMapper.updateOrder(order);
    }

    @Override
    @CacheEvict(cacheNames = "delOrder", allEntries = true, beforeInvocation = true, condition = "#id>0")
    public void deleteOrderById(int id) {
        ordersMapper.deleteOrderById(id);
    }

    @Override
    @Cacheable(cacheNames = "order")
    public void deleteOrderAll() {
        this.ordersMapper.deleteOrderAll();
    }

    @Override
    @Cacheable(cacheNames = "findOrderById")
    public List<Order> findOrderById(String id) {
        return this.ordersMapper.findOrderById(id);
    }

}
