package com.tna.yb_store.service;

import com.tna.yb_store.entity.Order;

import java.util.List;

public interface OrderService {

    void addOrder(Order order);

    List<Order> findOrderAll();

    void updateOrder(Order order);

    void deleteOrderById(int id);

    void deleteOrderAll();

    List<Order> findOrderById(String id);
}
