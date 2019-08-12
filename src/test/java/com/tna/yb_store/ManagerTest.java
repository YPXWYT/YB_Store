package com.tna.yb_store;

import com.tna.yb_store.service.ManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagerTest {

    @Autowired
    private ManagerService managerService;

    @Test
    public void checkManagerId(){
        System.out.println(managerService.checkManagerId("20742060214"));
        System.out.println("201742060214".equals(managerService.checkManagerId("201742060214")));
    }
}
