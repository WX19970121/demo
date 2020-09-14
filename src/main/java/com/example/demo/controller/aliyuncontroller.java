package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.example.demo.dao.AliyunDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/aliyun")
public class aliyuncontroller {

    @Autowired
    private AliyunDao aliyunDao;

    @RequestMapping("/SendMessToPhone")
    @ResponseBody
    public String SendMessToPhone(HttpServletRequest request) throws ClientException {
        String PhoneNum = (String) request.getParameter("PhoneNum");

        String code = aliyunDao.GetPhoneRandomNum(6);

        String Meg = aliyunDao.SendMessToPhone(PhoneNum, code);

        JSONObject Info = new JSONObject();
        Info.put("meg", Meg);

        return Info.toString();
    }

}
