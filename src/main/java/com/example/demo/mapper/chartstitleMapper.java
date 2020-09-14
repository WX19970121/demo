package com.example.demo.mapper;

import com.example.demo.pojo.ChartsTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface chartstitleMapper extends JpaRepository<ChartsTitle, Integer> {

    @Query("from ChartsTitle where chartsTitleId = :Id")
    public ChartsTitle queryByChartsTitleId(@Param("Id")long Id);

    @Query("from ChartsTitle")
    public List<ChartsTitle> queryAll();

}
