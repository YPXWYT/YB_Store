package com.tna.yb_store.entity;


import java.util.List;

public class TokenResponseBody extends BaseResponseBody {
    private String accessToken_key;
    private List<?> info;
    private YbUser user;

    public String getAccessToken_key() {
        return accessToken_key;
    }

    public void setAccessToken_key(String accessToken_key) {
        this.accessToken_key = accessToken_key;
    }

    public List<?> getInfo() {
        return info;
    }

    public void setInfo(List<?> info) {
        this.info = info;
    }

    public YbUser getUser() {
        return user;
    }

    public void setUser(YbUser user) {
        this.user = user;
    }
}
