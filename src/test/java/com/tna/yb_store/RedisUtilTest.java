package com.tna.yb_store;

import com.tna.yb_store.entity.Product;
import com.tna.yb_store.utils.MD5;
import com.tna.yb_store.utils.redis.RedisUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void setSO(){
        Product product = new Product(6,"象棋","象棋",20,"象棋图片",80,1,true,"ypx","ypx");
        if (redisUtil.set("product1",product)){
            System.out.println("缓存成功！");
        }else {
            System.out.println("缓存失败！");
        }
    }

    @Test
    public void removeKey(){
        redisUtil.remove("name");
    }

    @Test
    public void removeKeys(){
        redisUtil.remove("product1","product2");
    }

    @Test
    public void setSOL(){

        Product product = new Product(10,"象棋","象棋",20,"象棋图片",80,1,true,"ypx","ypx");

        if (redisUtil.set("product2",product, (long) 1000)){
            System.out.println("缓存成功！");
        }else {
            System.out.println("缓存失败！");
        }
    }

    @Test
    public  void MD5Test(){
        System.out.println(MD5.inputPassToFormPass("ypxwyt"));
        System.out.println(MD5.inputPassToDbPass(MD5.inputPassToFormPass("ypxwyt"),"ab123"));
        System.out.println(MD5.formPassToDBPass(MD5.inputPassToDbPass(MD5.inputPassToFormPass("ypxwyt"),"ab123"),"123ab"));
    }

    @Test
    public void get(){
        System.out.println(redisUtil.get("product2").toString());
    }
}
