package com.tna.yb_store.entity;

import java.util.List;

public class OrderAndProduct {
    private int id;
    private Product product;
    private String yb_userid;
    private String yb_username;
    private String yb_usernick;
    private char yb_sex;
    private String yb_money;
    private int status;
    private boolean is_enable;
    private String create_user;
    private String modify_user;
    private String create_time;
    private String modify_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getModify_time() {
        return modify_time;
    }

    public void setModify_time(String modify_time) {
        this.modify_time = modify_time;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getYb_userid() {
        return yb_userid;
    }

    public void setYb_userid(String yb_userid) {
        this.yb_userid = yb_userid;
    }

    public String getYb_username() {
        return yb_username;
    }

    public void setYb_username(String yb_username) {
        this.yb_username = yb_username;
    }

    public String getYb_usernick() {
        return yb_usernick;
    }

    public void setYb_usernick(String yb_usernick) {
        this.yb_usernick = yb_usernick;
    }

    public char getYb_sex() {
        return yb_sex;
    }

    public void setYb_sex(char yb_sex) {
        this.yb_sex = yb_sex;
    }

    public String getYb_money() {
        return yb_money;
    }

    public void setYb_money(String yb_money) {
        this.yb_money = yb_money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isIs_enable() {
        return is_enable;
    }

    public void setIs_enable(boolean is_enable) {
        this.is_enable = is_enable;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getModify_user() {
        return modify_user;
    }

    public void setModify_user(String modify_user) {
        this.modify_user = modify_user;
    }
}
