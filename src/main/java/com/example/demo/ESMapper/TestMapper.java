package com.example.demo.ESMapper;

import com.example.demo.pojo.ESTest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface TestMapper extends ElasticsearchRepository<ESTest, String> {

    public List<ESTest> findAllByMessInfoAndTitleInfoLike(String MessInfo, String TitleInfo);
}
