package com.tna.yb_store.service;

import com.tna.yb_store.entity.BaseResponseBody;
import com.tna.yb_store.entity.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {

    HttpEntity<BaseResponseBody> insertOrder(Order order);

    HttpEntity<BaseResponseBody> deleteOrderByUserId(String user_id);

    HttpEntity<BaseResponseBody> deleteOrderByOrderId(int id);

    HttpEntity<BaseResponseBody> updateOrder(Order order);

    HttpEntity<BaseResponseBody> selectOrderByOrderId(int id);

    HttpEntity<BaseResponseBody> selectOrderByUserId(String user_id);
}
