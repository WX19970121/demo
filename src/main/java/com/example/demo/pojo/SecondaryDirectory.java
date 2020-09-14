package com.example.demo.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "secondary_directory")
@ExcelTarget(value = "SecondaryId")
public class SecondaryDirectory implements Serializable {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name = "secondary_directory_id")
    private long Id; //序号

    @Column(name = "primary_directory_name")
    @Excel(name = "二级目录名称")
    private String secondaryDirectoryName;  //二级目录名字

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ExcelIgnore
    private PrimaryDirectory primaryDirectory; //对应的一级目录

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getSecondaryDirectoryName() {
        return secondaryDirectoryName;
    }

    public void setSecondaryDirectoryName(String secondaryDirectoryName) {
        this.secondaryDirectoryName = secondaryDirectoryName;
    }

    public PrimaryDirectory getPrimaryDirectory() {
        return primaryDirectory;
    }

    public void setPrimaryDirectory(PrimaryDirectory primaryDirectory) {
        this.primaryDirectory = primaryDirectory;
    }

    @Override
    public String toString() {
        return "SecondaryDirectory{" +
                "Id=" + Id +
                ", secondaryDirectoryName='" + secondaryDirectoryName + '\'' +
                ", primaryDirectoryId='" + primaryDirectory.getId() + '\'' +
                ", primaryDirectoryName=" + primaryDirectory.getPrimaryDirectoryName() +
                '}';
    }
}
