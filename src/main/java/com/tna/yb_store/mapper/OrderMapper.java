package com.tna.yb_store.mapper;


import com.tna.yb_store.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 添加一条订单信息
     *
     * @param order
     */
    int insertOrder(Order order);

    /**
     * 通过订单id来删除一条订单全部信息
     *
     * @param id
     */
    int deleteOrderByOrderId(int id);

    int deleteOrderByUserId(String user_id);

    /**
     * 删除所有订单信息
     */
    int deleteOrderAll();

    /**
     * 获取所有用户的全部订单信息
     *
     * @return
     */
    List<Order> selectOrderAll();


    /**
     * 更新一条订单全部信息
     *
     * @param order
     */
    int updateOrder(Order order);

    int updateStatuByOrderId(int status, String modify_user, int id);

    Order selectOrderByOrderId(int id);

    List<Order> selectOrderByUserId(String user_id);
}
