package com.example.demo.pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_role")
public class AdminRole {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Id
    @Column(name = "role_id")
    private long Id; //角色Id

    @Column(name = "role_name")
    private String roleName;  //角色名

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private AdminUser adminUser; //角色对应的用户

    @OneToMany(mappedBy="adminRole", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<RoleJurisdiction> roleJurisdictionList; //角色所拥有的权限

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public List<RoleJurisdiction> getRoleJurisdictionList() {
        return roleJurisdictionList;
    }

    public void setRoleJurisdictionList(List<RoleJurisdiction> roleJurisdictionList) {
        this.roleJurisdictionList = roleJurisdictionList;
    }
}
