package com.example.demo.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "normal_user")
@ExcelTarget(value = "UserId")
public class User implements Serializable {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @javax.persistence.Id
    @Column(name = "user_id")
    private long Id; //用户Id

    @Column(name = "user_name")
    @Excel(name = "用户姓名", width = 35)
    private String userName;  //用户名123456

    @Column(name = "user_phone")
    @Excel(name = "用户手机号", width = 35)
    private String userPhone;  //用户手机号

    @Column(name = "user_weixin")
    @Excel(name = "用户微信", width = 35)
    private String userWeiXin;  //用户微信

    @Column(name = "user_headimg")
    @ExcelIgnore
    private String userHeadImg;  //用户头像

    @Column(name = "user_headimg_path")
    @Excel(name = "用户头像", type = 2 ,width = 40, height = 50, imageType = 1)
    private String userHeadImgPath; //用户头像地址

    @Column(name = "user_autograph")
    @Excel(name = "用户签名", width = 35)
    private String userAutograph;  //用户签名

    @Column(name = "user_state")
    @Excel(name = "用户状态", width = 20)
    private String userState;  //用户状态

    @Column(name = "user_registertime")
    @Excel(name = "用户注册时间", databaseFormat = "yyyy-MM-dd", width = 35)
    private String userRegisterTime;  //用户注册时间

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserWeiXin() {
        return userWeiXin;
    }

    public void setUserWeiXin(String userWeiXin) {
        this.userWeiXin = userWeiXin;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getUserHeadImgPath() {
        return userHeadImgPath;
    }

    public void setUserHeadImgPath(String userHeadImgPath) {
        this.userHeadImgPath = userHeadImgPath;
    }

    public String getUserAutograph() {
        return userAutograph;
    }

    public void setUserAutograph(String userAutograph) {
        this.userAutograph = userAutograph;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getUserRegisterTime() {
        return userRegisterTime;
    }

    public void setUserRegisterTime(String userRegisterTime) {
        this.userRegisterTime = userRegisterTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userWeiXin='" + userWeiXin + '\'' +
                ", userAutograph='" + userAutograph + '\'' +
                ", userState='" + userState + '\'' +
                ", userRegisterTime='" + userRegisterTime + '\'' +
                '}';
    }
}
