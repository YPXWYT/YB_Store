package com.tna.yb_store.entity;

import java.util.List;

public class BaseResponseBody {
    private String status;
    private String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseResponseBody{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
