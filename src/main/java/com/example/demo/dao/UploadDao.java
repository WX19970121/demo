package com.example.demo.dao;

import org.springframework.stereotype.Service;

@Service
public class UploadDao {

    public String GetFieStuffix(String FileName){
        String suffix = FileName.substring(FileName.lastIndexOf(".")); //获取文件的后缀名，带.号
        return suffix;
    }

}
