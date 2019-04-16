package com.tna.yb_store;

import com.tna.yb_store.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigureTest {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Test
    @Cacheable("product")
    public void testRedisTemplate(){
        redisTemplate.opsForValue().set("key1", "value1");
        Product product = new Product(6,"象棋","象棋",20,"象棋图片",80,1,true,"ypx","ypx");
        Product product1 = new Product(5,"象棋","象棋",20,"象棋图片",80,1,true,"ypx","ypx");

        redisTemplate.opsForList().rightPush("product",product);
        redisTemplate.opsForList().rightPush("product",product1);

        System.out.println(redisTemplate.opsForValue().get("key1"));
        //查询索引0到商品总数-1索引（也就是查出所有的商品）
        List<Object> prodList = redisTemplate.opsForList().range("product", 0,redisTemplate.opsForList().size("pruduct")-1);
        for(Object obj:prodList){
            System.out.println(obj);
        }
    }
}
