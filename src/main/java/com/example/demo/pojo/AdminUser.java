package com.example.demo.pojo;

import javax.persistence.*;

@Entity
@Table(name = "admin_user")
public class AdminUser {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name = "admin_id")
    private long Id; //管理员Id

    @Column(name = "admin_name")
    private String adminName;  //管理员账户

    @Column(name = "admin_password")
    private String adminPassword;  //管理员密码

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
