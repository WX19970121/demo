package com.example.demo.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "primary_directory")
@ExcelTarget(value = "PrimaryId")
public class PrimaryDirectory implements Serializable {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name = "primary_directory_id")
    private long Id; //序号

    @Column(name = "primary_directory_name")
    @Excel(name = "一级目录名称", needMerge = true)
    private String primaryDirectoryName;  //一级目录名字

    @OneToMany(mappedBy="primaryDirectory", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @ExcelCollection(name = "二级目录", orderNum = "4")
    private List<SecondaryDirectory> secondaryDirectoryList; //一级目录所拥有的二级目录

    @Transient
    private int primaryDirectoryNum; //所拥有的二级目录数量

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getPrimaryDirectoryName() {
        return primaryDirectoryName;
    }

    public void setPrimaryDirectoryName(String primaryDirectoryName) {
        this.primaryDirectoryName = primaryDirectoryName;
    }

    public List<SecondaryDirectory> getSecondaryDirectoryList() {
        return secondaryDirectoryList;
    }

    public void setSecondaryDirectoryList(List<SecondaryDirectory> secondaryDirectoryList) {
        this.secondaryDirectoryList = secondaryDirectoryList;
    }

    public int getPrimaryDirectoryNum() {
        return primaryDirectoryNum;
    }

    public void setPrimaryDirectoryNum(int primaryDirectoryNum) {
        this.primaryDirectoryNum = primaryDirectoryNum;
    }

    @Override
    public String toString() {
        return "PrimaryDirectory{" +
                "Id=" + Id +
                ", primaryDirectoryName='" + primaryDirectoryName + '\'' +
                '}';
    }
}
