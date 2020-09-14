package com.example.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.OtherDao;
import com.example.demo.mapper.JournalMapper;
import com.example.demo.pojo.AdminUser;
import com.example.demo.pojo.Journal;
import com.example.demo.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class UserAspect {

    @Pointcut("execution(* com.example.demo.controller.usercontroller.UserEdit(..))")
    public void UserEdit(){}

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private OtherDao otherDao;

    @Autowired
    private HttpServletRequest request;

    //登录的管理员名称
    private String LoginAdminName = null;


    //用于记录User编辑的各种日志
    @AfterReturning(returning = "evt", pointcut = "UserEdit()")
    public void UserJournal(JoinPoint joinPoint, Object evt) {

        //需要录入数据库的日志信息
        String journalMeg = null;

        //获取
        AdminUser loginAdmin = (AdminUser) request.getSession().getAttribute("adminUser");
        if(loginAdmin == null){
            LoginAdminName = "无名";
        }
        else {
            LoginAdminName = loginAdmin.getAdminName();
        }

        //获取返回的值
        String code = JSON.parseObject((String) evt).getString("meg");

        //获取需要编辑的用户数据
        User user = (User) joinPoint.getArgs()[1];

        if("ADD SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "向数据库用户信息表中添加了用户信息为\""+user.toString()+"\"的用户。";
        }
        else if("CHANGE STATUS SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "将用户ID为\""+user.getId()+"\"，" + "手机号为\""+user.getUserPhone()+"\""
            + "的用户状态改为了\""+user.getUserState()+"\"。";
        }
        else if("DELETE SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "删除了用户ID为\""+user.getId()+"\"，" + "手机号为\""+user.getUserPhone()+"\""
            + "的用户。";
        }

        if(journalMeg != null && !"".equals(journalMeg)){
            Journal Info = new Journal();
            Info.setJournalMessage(journalMeg);
            Info.setJournalTime(otherDao.GetNowTime());

            journalMapper.save(Info);
        }
    }

}
