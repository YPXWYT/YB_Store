package com.tna.yb_store.entity;

public class Product {
    private int id;
    private String name;
    private String describe;
    private int number;
    private String img;
    private int price;
    private int status;
    private boolean is_enable;
    private String create_user;
    private String modify_user;

    public Product(int id, String name, String describe, int number, String img, int price, int status, boolean is_enable, String create_user, String modify_user) {
        this.id = id;
        this.name = name;
        this.describe = describe;
        this.number = number;
        this.img = img;
        this.price = price;
        this.status = status;
        this.is_enable = is_enable;
        this.create_user = create_user;
        this.modify_user = modify_user;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", number=" + number +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", is_enable=" + is_enable +
                ", create_user='" + create_user + '\'' +
                ", modify_user='" + modify_user + '\'' +
                '}';
    }
}
