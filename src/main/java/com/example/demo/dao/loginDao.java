package com.example.demo.dao;

import com.example.demo.mapper.adminMapper;
import com.example.demo.pojo.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class loginDao {

    @Autowired
    private adminMapper adminMapper;

    public AdminUser queryAdminUserByAdminName(String adminName){
        return adminMapper.queryAdminUserByAdminName(adminName);
    }

}
