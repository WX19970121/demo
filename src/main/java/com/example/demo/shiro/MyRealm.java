package com.example.demo.shiro;

import com.example.demo.mapper.adminMapper;
import com.example.demo.pojo.AdminRole;
import com.example.demo.pojo.AdminUser;
import com.example.demo.pojo.RoleJurisdiction;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private adminMapper adminMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) principalCollection.getPrimaryPrincipal();

        AdminUser adminUser = adminMapper.queryAdminUserByAdminName(userName);

        List<String> roles = new ArrayList<String>(); //角色信息
        List<String> jurisdictions = new ArrayList<String>(); //权限信息

        for(AdminRole adminRole : adminUser.getAdminRoleList()){
            for(RoleJurisdiction roleJurisdiction : adminRole.getRoleJurisdictionList()){
                jurisdictions.add(roleJurisdiction.getJurisdictionName());
            }
            roles.add(adminRole.getRoleName());
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(jurisdictions);

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();

        AdminUser adminUser = adminMapper.queryAdminUserByAdminName(userName);

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(adminUser.getAdminName(), adminUser.getAdminPassword(), ByteSource.Util.bytes(adminUser.getAdminSalt()), this.getName());

        return simpleAuthenticationInfo;
    }
}
