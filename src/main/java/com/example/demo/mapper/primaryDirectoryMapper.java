package com.example.demo.mapper;

import com.example.demo.pojo.PrimaryDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface primaryDirectoryMapper extends JpaRepository<PrimaryDirectory, Integer> {

    @Query("from PrimaryDirectory")
    public List<PrimaryDirectory> queryAll();

    @Query("from PrimaryDirectory where Id = :Id")
    public PrimaryDirectory queryById(@Param("Id")long Id);

}
