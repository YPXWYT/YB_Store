package com.tna.yb_store;

import com.tna.yb_store.entity.Order;
import com.tna.yb_store.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdersServiceTest {

    @Autowired
    private OrderService ordersService;

    @Test
    public void addOrder(){
        Order order = new Order(1003,"2003","听海哭的声音","听海哭的声音",'M',"100",0,true,"ypx","ypx");
        ordersService.addOrder(order);
    }

    @Test
    public void findOrderAll(){
        List<Order> orders = ordersService.findOrderAll();
        System.out.println(orders);
    }

    @Test
    public void updateOrder(){
        Order order = new Order(1001,"2001","男孩","男孩",'M',"100",1,true,"ypx","ypx");
        ordersService.updateOrder(order);
    }

    @Test
    public void deleteOrderById(){
        ordersService.deleteOrderById(2);
    }

    @Test
    public void deleteOrderAll(){
        ordersService.deleteOrderAll();
    }

    @Test
    public void findOrderById(){
        List<Order> orders = ordersService.findOrderById("2005");
        System.out.println(orders);
    }
}
