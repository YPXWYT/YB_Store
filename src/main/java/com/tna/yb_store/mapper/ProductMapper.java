package com.tna.yb_store.mapper;

import com.tna.yb_store.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    /**
     * 添加一条产品信息
     *
     * @param product
     */
    void addProduct(Product product);

    /**
     * 获取所有产品的全部订单信息
     *
     * @return
     */
    List<Product> findProductAll();

    /**
     * 更新一条产品的全部信息
     *
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 删除一条产品的全部信息
     *
     * @param id
     */
    void deleteProductById(int id);

    /**
     * 删除所有产品信息
     */
    void deleteProductAll();
}
