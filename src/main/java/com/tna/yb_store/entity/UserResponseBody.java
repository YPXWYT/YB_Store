package com.tna.yb_store.entity;

public class UserResponseBody {
    private int code;
    private String msg;
    private YbUser ybUser;

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

    public YbUser getYbUser() {
        return ybUser;
    }

    public void setYbUser(YbUser ybUser) {
        this.ybUser = ybUser;
    }
}
