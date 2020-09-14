package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.OtherDao;
import com.example.demo.mapper.chartstitleMapper;
import com.example.demo.pojo.ChartsTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/Echarts")
public class EchartsController {

    @Autowired
    private chartstitleMapper chartstitleMapper;

    @Autowired
    private OtherDao otherDao;

    @RequestMapping("/GoUserDistributionPage")
    public ModelAndView GoGoUserDistributionPage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/user-manager-UserDistributionCharts");
        return modelAndView;
    }

    @RequestMapping("/UserDistributionCharts")
    @ResponseBody
    public String GetUserDistribution(){
        List<ChartsTitle> listInfo = chartstitleMapper.queryAll();

        //需要过滤的Json数据
        String[] FilteringData = {"chartsTitleId", "chartsValueId", "chartsTitle"};
        JSONArray ChartsInfo = otherDao.FilterJSONArrar(FilteringData, listInfo);

        JSONObject meg = new JSONObject();
        meg.put("code", "OK");
        meg.put("data", ChartsInfo);

        return meg.toJSONString();
    }

}
