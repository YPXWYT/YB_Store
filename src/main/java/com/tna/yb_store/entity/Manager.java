package com.tna.yb_store.entity;

public class Manager {
    private int id;
    private String manager_id;
    private String manager_name;
    private String manager_department;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getManager_department() {
        return manager_department;
    }

    public void setManager_department(String manager_department) {
        this.manager_department = manager_department;
    }
}
