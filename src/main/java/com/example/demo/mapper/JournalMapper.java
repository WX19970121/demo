package com.example.demo.mapper;

import com.example.demo.pojo.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JournalMapper extends JpaRepository<Journal, Integer> {

    @Query("from Journal order by journalTime desc ")
    public List<Journal> queryAll();

}
