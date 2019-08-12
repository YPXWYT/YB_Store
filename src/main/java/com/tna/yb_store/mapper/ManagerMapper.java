package com.tna.yb_store.mapper;


import com.tna.yb_store.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerMapper {
    String checkManagerId(String manager_id);
}
