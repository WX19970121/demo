package com.example.demo.mapper;

import com.example.demo.pojo.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface adminMapper extends JpaRepository<AdminUser, Integer> {

    @Query("from AdminUser where adminName = :adminName")
    public AdminUser queryAdminUserByAdminName(@Param("adminName")String adminName);

}
