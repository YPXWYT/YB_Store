package com.tna.yb_store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.tna.yb_store.mapper")
@EnableCaching
public class YbStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(YbStoreApplication.class, args);
    }

}
