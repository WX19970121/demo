package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface userMapper extends JpaRepository<User, Integer> {

    @Query("from User order by userRegisterTime desc")
    public List<User> queryAll();

    @Query("from User where Id = :Id")
    public User queryById(@Param("Id")long Id);

    @Query("from User where userPhone = :userPhone")
    public User queryByUserPhone(@Param("userPhone")String userPhone);

}
