package com.tna.yb_store.entity;

import java.util.List;

public class BaseResponseBody {
    private int code;
    private String msg;
    private List<?> data;

    private BaseResponseBody() {
    }

    ;

    public static BaseResponseBody getInstance() {
        return new BaseResponseBody();
    }

    public static BaseResponseBody checkSelectAll(BaseResponseBody baseResponseBody, List<?> list) {

        if (list != null) {
            baseResponseBody.setCode(0);
            baseResponseBody.setData(list);
            baseResponseBody.setMsg("查询成功！");
            if (list.isEmpty()) {
                baseResponseBody.setCode(1);
                baseResponseBody.setData(list);
                baseResponseBody.setMsg("没有更多东西啦~");
            }
        } else {
            baseResponseBody.setCode(2);
            baseResponseBody.setMsg("数据库错误！");
        }
        return baseResponseBody;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
