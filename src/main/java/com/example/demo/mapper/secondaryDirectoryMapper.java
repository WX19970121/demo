package com.example.demo.mapper;

import com.example.demo.pojo.SecondaryDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface secondaryDirectoryMapper extends JpaRepository<SecondaryDirectory, Integer> {

    @Query("from SecondaryDirectory where primaryDirectory.Id = :Id")
    public List<SecondaryDirectory> queryByPrimaryDirectoryId(@Param("Id")long Id);

    @Query("from SecondaryDirectory where Id = :Id")
    public SecondaryDirectory queryById(@Param("Id")long Id);

}
