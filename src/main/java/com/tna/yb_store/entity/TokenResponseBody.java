package com.tna.yb_store.entity;


import java.util.List;

public class TokenResponseBody extends BaseResponseBody{
    private String token;
    private List<?> info;
    private YbUser user;

    public YbUser getUser() {
        return user;
    }

    public void setUser(YbUser user) {
        this.user = user;
    }

    public List<?> getInfo() {
        return info;
    }

    public void setInfo(List<?> info) {
        this.info = info;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
