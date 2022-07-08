package com.example.jkm_web.model;

public class Permissions {
    Integer permissionsId;
    String userId;

    @Override
    public String toString() {
        return "Permissions{" +
                "permissionsId=" + permissionsId +
                ", userId='" + userId + '\'' +
                '}';
    }

    public Integer getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(Integer permissionsId) {
        this.permissionsId = permissionsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
