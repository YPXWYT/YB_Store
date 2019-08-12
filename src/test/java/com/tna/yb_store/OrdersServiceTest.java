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
    public void addOrder() {
        int product_id = 2003;
        for (int i = 0; i < 10; i++) {
            if (i%2==0){
                Order order = new Order(product_id, "11650216", "听海哭的声音", "听海哭的声音", 'M', "100", 0, true, "ypx", "ypx");
                ordersService.insertOrder(order);
            }else {
                Order order = new Order(product_id, "11650217", "听海哭的声音", "听海哭的声音", 'M', "100", 0, true, "ypx", "ypx");
                ordersService.insertOrder(order);
            }
            product_id++;
        }
    }


    @Test
    public void updateOrder() {
        Order order = new Order(1001, "2001", "男孩", "男孩", 'M', "100", 1, true, "ypx", "ypx");
        ordersService.updateOrder(order);
    }

    @Test
    public void deleteOrderById() {
        ordersService.deleteOrderByOrderId(2);
    }

    @Test
    public void findOrderById() {

    }
}
