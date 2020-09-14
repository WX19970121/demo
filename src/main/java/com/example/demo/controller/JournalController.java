package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.OtherDao;
import com.example.demo.mapper.JournalMapper;
import com.example.demo.pojo.Journal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/Journal-Manager")
public class JournalController {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private OtherDao otherDao;

    @RequestMapping("/GoInfoPage")
    public ModelAndView GoInfoPage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/journal-manager");
        return  modelAndView;
    }

    @RequestMapping("/GetAllJournalInfo")
    @ResponseBody
    public String GetAllJournalInfo(HttpServletRequest request){
        int page = Integer.parseInt((String) request.getParameter("page"));
        int rows = Integer.parseInt((String) request.getParameter("rows"));

        List<Journal> querylist = (List<Journal>) request.getSession().getAttribute("journalList");

        if(page == 1 || querylist == null){
            querylist = journalMapper.queryAll();
            request.getSession().setAttribute("journalList", querylist);
        }

        int total = querylist.size();
        int Pagetotal = otherDao.GetPagNum(total, rows);

        int StartIndex = (page - 1) * rows;
        int EndIndex = page * rows;

        if(EndIndex > total){
            EndIndex = total;
        }

        //需要过滤的Json数据
        String[] FilteringData = {};
        JSONArray RowInfo = otherDao.FilterJSONArrar(FilteringData, querylist.subList(StartIndex, EndIndex));

        JSONObject TableInfo = new JSONObject();
        TableInfo.put("total", Pagetotal);
        TableInfo.put("page", page);
        TableInfo.put("records", total);
        TableInfo.put("rows", RowInfo);

        return TableInfo.toString();
    }

    //清空全部日志
    @RequestMapping("/ClearAllJournal")
    @ResponseBody
    public String ClearAllJournal(){
        JSONObject Info = new JSONObject();
        journalMapper.deleteAll();

        Info.put("meg", "CLEAR SUCCESS");
        return Info.toString();
    }

}
