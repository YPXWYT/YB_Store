package com.tna.yb_store.service.Impl;

import com.tna.yb_store.mapper.ManagerMapper;
import com.tna.yb_store.service.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerMapper managerMapper;

    public ManagerServiceImpl(ManagerMapper managerMapper) {
        this.managerMapper = managerMapper;
    }

    @Override
    public String checkManagerId(String manager_id) {
        return managerMapper.checkManagerId(manager_id);
    }
}
