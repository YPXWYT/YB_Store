package com.tna.yb_store.entity;

import java.util.List;

public class ProductAndOrderResponseBody extends BaseResponseBody{
    private List<?> info;

    public List<?> getInfo() {
        return info;
    }

    public void setInfo(List<?> info) {
        this.info = info;
    }
}
