package com.tna.yb_store;

import com.tna.yb_store.entity.Product;
import com.tna.yb_store.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void addProduct() {
        Product product = new Product(10, "蓝球", "蓝球", 30, "蓝球图片", 80, 1, true, "ypx", "ypx");
        productService.addProduct(product);
    }

    @Test
    public void findProductAll() {
        List<Product> products = productService.findProductAll();
        System.out.println(products);
    }

    @Test
    public void updateProduct() {
        Product product = new Product(6, "象棋", "象棋", 20, "象棋图片", 80, 1, true, "ypx", "ypx");
        productService.updateProduct(product);
    }

    @Test
    public void deleteProductById() {
        productService.deleteProductById(10);
    }

    @Test
    public void deleteProductAll() {
        productService.deleteProductAll();
    }
}
