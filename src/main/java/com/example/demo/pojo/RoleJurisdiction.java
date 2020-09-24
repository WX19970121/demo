package com.example.demo.pojo;

import javax.persistence.*;

@Entity
@Table(name = "role_jurisdiction")
public class RoleJurisdiction {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Id
    @Column(name = "jurisdiction_id")
    private long Id; //角色Id

    @Column(name = "jurisdiction_name")
    private String jurisdictionName;  //权限名

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private AdminRole adminRole; //权限所对应的角色

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getJurisdictionName() {
        return jurisdictionName;
    }

    public void setJurisdictionName(String jurisdictionName) {
        this.jurisdictionName = jurisdictionName;
    }

    public AdminRole getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(AdminRole adminRole) {
        this.adminRole = adminRole;
    }
}
