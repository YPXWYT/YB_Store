package com.tna.yb_store.mapper;


import com.tna.yb_store.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 添加一条订单信息
     *
     * @param order
     */
    void addOrder(Order order);

    /**
     * 获取所有用户的全部订单信息
     *
     * @return
     */
    List<Order> findOrderAll();

    /**
     * 通过用户的id来查询用户的订单信息
     *
     * @param id
     * @return
     */
    List<Order> findOrderById(String id);

    /**
     * 更新一条订单全部信息
     *
     * @param order
     */
    void updateOrder(Order order);

    /**
     * 通过订单id来删除一条订单全部信息
     *
     * @param id
     */
    void deleteOrderById(int id);

    /**
     * 删除所有订单信息
     */
    void deleteOrderAll();
}
