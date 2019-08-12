package com.tna.yb_store.mapper;

import com.tna.yb_store.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    int insertProduct(Product product);

    List<Product> selectProductAll(int currIndex, int sizePerPage);

    Product selectProductById(int product_id);

    int updateProductNumber(int product_id);

    int selectProductNumberById(int product_id);

}
