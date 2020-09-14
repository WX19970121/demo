package com.example.demo.aspect;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.OtherDao;
import com.example.demo.mapper.JournalMapper;
import com.example.demo.pojo.AdminUser;
import com.example.demo.pojo.Journal;
import com.example.demo.pojo.PrimaryDirectory;
import com.example.demo.pojo.SecondaryDirectory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class DirectoryAspect {

    @Pointcut("execution(* com.example.demo.controller.directorycontroller.EditPrimaryDirectory(..))")
    public void EditPrimaryDirectory(){}

    @Pointcut("execution(* com.example.demo.controller.directorycontroller.EditSecondaryDirectory(..))")
    public void EditSecondaryDirectory(){}

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private OtherDao otherDao;

    @Autowired
    private HttpServletRequest request;

    //登录的管理员名称
    private String LoginAdminName = null;


    //用于记录一级目录编辑的各种日志
    @AfterReturning(returning = "evt", pointcut = "EditPrimaryDirectory()")
    public void PrimaryDirectoryJournal(JoinPoint joinPoint, Object evt){
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

        //获取编辑的一级目录信息
        PrimaryDirectory primaryDirectory = (PrimaryDirectory) joinPoint.getArgs()[1];

        if("ADD SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "添加了信息为\""+primaryDirectory.toString()+"\"的一级目录";
        }
        else if("UPDATE SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "修改了一级目录信息\""+JSON.parseObject((String) evt).getString("OldPrimaryDirectoryInfo")+"\""
            + "为\""+JSON.parseObject((String) evt).getString("NewPrimaryDirectoryInfo")+"\"";
        }
        else if("DELETE SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "删除了Id为\""+primaryDirectory.getId()+"\"，"
                    + "名称为:\""+primaryDirectory.getPrimaryDirectoryName()+"\"的一级目录";
        }

        if(journalMeg != null && !"".equals(journalMeg)){
            Journal Info = new Journal();
            Info.setJournalMessage(journalMeg);
            Info.setJournalTime(otherDao.GetNowTime());

            journalMapper.save(Info);
        }
    }

    //用于记录二级目录编辑的各种日志
    @AfterReturning(returning = "evt", pointcut = "EditSecondaryDirectory()")
    public void SecondaryDirectoryJournal(JoinPoint joinPoint, Object evt){
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

        //获取编辑的一级目录信息
        SecondaryDirectory secondaryDirectory = (SecondaryDirectory) joinPoint.getArgs()[1];

        if("ADD SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "添加了信息为\""+secondaryDirectory.toString()+"\"的二级目录";
        }
        else if("UPDATE SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "修改了二级目录信息\""+JSON.parseObject((String) evt).getString("OldSecondaryDirectoryInfo")+"\""
                    + "为\""+JSON.parseObject((String) evt).getString("NewSecondaryDirectoryInfo")+"\"";
        }
        else if("DELETE SUCCESS".equals(code)){
            journalMeg = "管理员\""+LoginAdminName+"\"" + "删除了Id为\""+secondaryDirectory.getId()+"\"，"
                    + "一级目录\""+secondaryDirectory.getPrimaryDirectory().toString()+"\"下"
                    + "Id为:\""+secondaryDirectory.getId()+"\"，"
                    + "名称为:\""+secondaryDirectory.getSecondaryDirectoryName()+"\"的二级目录";
        }

        if(journalMeg != null && !"".equals(journalMeg)){
            Journal Info = new Journal();
            Info.setJournalMessage(journalMeg);
            Info.setJournalTime(otherDao.GetNowTime());

            journalMapper.save(Info);
        }
    }

}
