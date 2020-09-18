package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.OtherDao;
import com.example.demo.mapper.primaryDirectoryMapper;
import com.example.demo.mapper.secondaryDirectoryMapper;
import com.example.demo.pojo.PrimaryDirectory;
import com.example.demo.pojo.SecondaryDirectory;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/directory-manager")
public class directorycontroller {

    @Autowired
    private primaryDirectoryMapper primaryDirectoryMapper; //我的世界

    @Autowired
    private secondaryDirectoryMapper secondaryDirectoryMapper;

    @Autowired
    private OtherDao otherDao;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/GoInfoPage")
    public ModelAndView EnterInfoPage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/directory-manager");
        return modelAndView;
    }

    @RequestMapping("/GoAddAndUpdatePrimaryDirectoryPage")
    public ModelAndView EnterAddAndUpdatePage(HttpServletRequest request, ModelAndView modelAndView){
        String PrimaryDirectoryId = (String) request.getParameter("PrimaryDirectoryId");

        PrimaryDirectory AddAndUpdateInfo = null; //一级目录信息
        String type = ""; //编辑类型

        if("0".equals(PrimaryDirectoryId)){
            AddAndUpdateInfo = new PrimaryDirectory();
            AddAndUpdateInfo.setId(0);
            AddAndUpdateInfo.setPrimaryDirectoryName("");

            type = "add";
        }
        else {
            AddAndUpdateInfo = primaryDirectoryMapper.queryById(Long.parseLong(PrimaryDirectoryId));

            type = "update";
        }

        modelAndView.addObject("PrimaryDirectory", AddAndUpdateInfo);
        modelAndView.addObject("type", type);

        modelAndView.setViewName("admin/directory-manager-addAndupdate-primarydirectory");

        return modelAndView;
    }

    @RequestMapping("/getPrimaryDirectoryInfo")
    @ResponseBody
    public String GetPrimaryDirectoryInfo(HttpServletRequest request){
        int page = Integer.parseInt((String) request.getParameter("page"));
        int rows = Integer.parseInt((String) request.getParameter("rows"));

        List<PrimaryDirectory> querylist = new ArrayList<PrimaryDirectory>();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if(page == 1){
            redisTemplate.delete("AllPrimaryDirectoryInfo");
        }

        if(redisTemplate.hasKey("AllPrimaryDirectoryInfo")){
            querylist = (List<PrimaryDirectory>) valueOperations.get("AllPrimaryDirectoryInfo");
        }
        else {
            querylist = primaryDirectoryMapper.queryAll();
            valueOperations.set("AllPrimaryDirectoryInfo", querylist);
        }

        int total = querylist.size();
        int Pagetotal = otherDao.GetPagNum(total, rows);

        int StartIndex = (page - 1) * rows;
        int EndIndex = page * rows;

        if(EndIndex > total){
            EndIndex = total;
        }

        //统计二级目录数量
        for(PrimaryDirectory primaryDirectory : querylist){
            primaryDirectory.setPrimaryDirectoryNum(primaryDirectory.getSecondaryDirectoryList().size());
        }

        //需要过滤的Json数据
        String[] FilteringData = {"secondaryDirectoryList"};
        JSONArray RowInfo = otherDao.FilterJSONArrar(FilteringData, querylist.subList(StartIndex, EndIndex));

        JSONObject TableInfo = new JSONObject();
        TableInfo.put("total", Pagetotal);
        TableInfo.put("page", page);
        TableInfo.put("records", total);
        TableInfo.put("rows", RowInfo);

        return  TableInfo.toString();
    }

    @RequestMapping("/editPrimaryDirectory")
    @ResponseBody
    public String EditPrimaryDirectory(HttpServletRequest request, PrimaryDirectory primaryDirectory){
        JSONObject Info = new JSONObject();

        String edit = (String) request.getParameter("edit");

        String meg = ""; //最后输出的提示信息

        if("add".equals(edit)){
            primaryDirectoryMapper.save(primaryDirectory);
            meg = "ADD SUCCESS";
        }
        if("update".equals(edit)){
            //老的一级目录信息与新的一级目录信息，用于日志的记录
            JSONObject OldPrimaryDirectoryInfo = null;
            JSONObject NewPrimaryDirectoryInfo = null;

            PrimaryDirectory updateInfo = primaryDirectoryMapper.queryById(primaryDirectory.getId());

            String[] Filter = {"secondaryDirectoryList", "primaryDirectoryNum"};
            OldPrimaryDirectoryInfo = otherDao.FilterJSONObject(Filter, updateInfo);

            updateInfo.setPrimaryDirectoryName(primaryDirectory.getPrimaryDirectoryName());
            primaryDirectoryMapper.saveAndFlush(updateInfo);

            meg = "UPDATE SUCCESS";

            NewPrimaryDirectoryInfo = otherDao.FilterJSONObject(Filter, updateInfo);

            Info.put("OldPrimaryDirectoryInfo", OldPrimaryDirectoryInfo);
            Info.put("NewPrimaryDirectoryInfo", NewPrimaryDirectoryInfo);

        }

        if("delete".equals(edit)){
            PrimaryDirectory deleteinfo = primaryDirectoryMapper.queryById(primaryDirectory.getId());

            //判断一级目录下是否存在二级目录,拥有二级目录的一级目录无法删除
            if (deleteinfo.getSecondaryDirectoryList().size() <= 0 || deleteinfo.getSecondaryDirectoryList() == null){
                primaryDirectoryMapper.delete(deleteinfo);

                meg = "DELETE SUCCESS";

                primaryDirectory.setPrimaryDirectoryName(deleteinfo.getPrimaryDirectoryName());
            }
            else {
                meg = "SECONDARY DIRECTORY EXISTS";
            }
        }

        Info.put("meg", meg);

        return Info.toString();
    }

    //前往编辑二级目录的页面
    @RequestMapping("/GoAddAndUpdateSecondaryDirectoryPage")
    public ModelAndView EnterAddAndUpdateSecondaryDirectoryPage(HttpServletRequest request, ModelAndView modelAndView){
        String SecondaryDirectoryId = (String) request.getParameter("SecondaryDirectoryId");

        //下拉菜单的各种信息
        List<PrimaryDirectory> SelectInfo = primaryDirectoryMapper.queryAll();

        if("0".equals(SecondaryDirectoryId)){
            PrimaryDirectory primaryDirectoryInfo = new PrimaryDirectory();
            primaryDirectoryInfo.setId(0);
            primaryDirectoryInfo.setPrimaryDirectoryName("");

            SecondaryDirectory secondaryDirectoryInfo = new SecondaryDirectory();
            secondaryDirectoryInfo.setId(0);
            secondaryDirectoryInfo.setSecondaryDirectoryName("");
            secondaryDirectoryInfo.setPrimaryDirectory(primaryDirectoryInfo);

            modelAndView.addObject("SecondaryDirectory", secondaryDirectoryInfo);
            modelAndView.addObject("PrimaryDirectory", SelectInfo);
            modelAndView.addObject("type", "add");
        }
        else {
            SecondaryDirectory secondaryDirectoryInfo = secondaryDirectoryMapper.queryById(Long.parseLong(SecondaryDirectoryId));

            modelAndView.addObject("SecondaryDirectory", secondaryDirectoryInfo);
            modelAndView.addObject("PrimaryDirectory", SelectInfo);
            modelAndView.addObject("type", "update");
        }

        modelAndView.setViewName("admin/directory-manager-addAndupdate-secondarydirectory");

        return modelAndView;
    }

    //获取二级目录信息
    @RequestMapping("/getSecondaryDirectoryInfo")
    @ResponseBody
    public String GetSecondaryDirectoryInfo(HttpServletRequest request){
        int page = Integer.parseInt((String) request.getParameter("page"));
        int rows = Integer.parseInt((String) request.getParameter("rows"));

        long BelongPrimaryDirectoryId = Long.parseLong((String)request.getParameter("BelongPrimaryDirectoryId"));

        List<SecondaryDirectory> querylist = new ArrayList<SecondaryDirectory>();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if(page == 1){
            redisTemplate.delete(BelongPrimaryDirectoryId + "_SecondaryDirectoryInfo");
        }

        if(redisTemplate.hasKey(BelongPrimaryDirectoryId + "_SecondaryDirectoryInfo")){
            querylist = (List<SecondaryDirectory>) valueOperations.get(BelongPrimaryDirectoryId + "_SecondaryDirectoryInfo");
        }
        else {
            querylist = secondaryDirectoryMapper.queryByPrimaryDirectoryId(BelongPrimaryDirectoryId);
            valueOperations.set(BelongPrimaryDirectoryId + "_SecondaryDirectoryInfo", querylist);
        }

        int total = querylist.size();
        int Pagetotal = otherDao.GetPagNum(total, rows);

        int StartIndex = (page - 1) * rows;
        int EndIndex = page * rows;

        if(EndIndex > total){
            EndIndex = total;
        }

        //需要过滤的Json数据
        String[] FilteringData = {"primaryDirectory"};
        JSONArray RowInfo = otherDao.FilterJSONArrar(FilteringData, querylist.subList(StartIndex, EndIndex));

        JSONObject TableInfo = new JSONObject();
        TableInfo.put("total", Pagetotal);
        TableInfo.put("page", page);
        TableInfo.put("records", total);
        TableInfo.put("rows", RowInfo);

        return  TableInfo.toString();
    }

    //编辑二级目录信息，提示获取的一级目录信息的标记为"belongId"
    @RequestMapping("/editSecondaryDirectory")
    @ResponseBody
    public String EditSecondaryDirectory(HttpServletRequest request, SecondaryDirectory secondaryDirectory){
        JSONObject Info = new JSONObject();

        String edit = (String) request.getParameter("edit");
        String meg = "";

        if("add".equals(edit)){
            //获取二级目录所对应的一级目录
            long belongPrimaryDirectoryId = Long.parseLong((String) request.getParameter("belongId"));
            PrimaryDirectory BelongPrimaryDirectory = primaryDirectoryMapper.queryById(belongPrimaryDirectoryId);

            secondaryDirectory.setPrimaryDirectory(BelongPrimaryDirectory);
            secondaryDirectoryMapper.save(secondaryDirectory);

            meg = "ADD SUCCESS";
        }

        if("update".equals(edit)){
            //老的二级目录信息与新的二级目录信息，用于日志的记录
            JSONObject OldSecondaryDirectoryInfo = null;
            JSONObject NewSecondaryDirectoryInfo = null;

            //获取二级目录所对应的一级目录
            long belongPrimaryDirectoryId = Long.parseLong((String) request.getParameter("belongId"));
            PrimaryDirectory BelongPrimaryDirectory = primaryDirectoryMapper.queryById(belongPrimaryDirectoryId);

            //需要更新的二级目录信息
            SecondaryDirectory UpdateInfo = secondaryDirectoryMapper.queryById(secondaryDirectory.getId());

            String[] Filter = {"secondaryDirectoryList", "primaryDirectoryNum"};
            OldSecondaryDirectoryInfo = otherDao.FilterJSONObject(Filter, UpdateInfo);

            UpdateInfo.setSecondaryDirectoryName(secondaryDirectory.getSecondaryDirectoryName());
            UpdateInfo.setPrimaryDirectory(BelongPrimaryDirectory);

            //更新二级目录信息
            secondaryDirectoryMapper.saveAndFlush(UpdateInfo);

            meg = "UPDATE SUCCESS";

            NewSecondaryDirectoryInfo = otherDao.FilterJSONObject(Filter, UpdateInfo);

            Info.put("OldSecondaryDirectoryInfo", OldSecondaryDirectoryInfo);
            Info.put("NewSecondaryDirectoryInfo", NewSecondaryDirectoryInfo);
        }

        if("delete".equals(edit)){
            SecondaryDirectory DeleteInfo = secondaryDirectoryMapper.queryById(secondaryDirectory.getId());

            //删除二级目录
            secondaryDirectoryMapper.delete(DeleteInfo);

            meg = "DELETE SUCCESS";

            secondaryDirectory.setSecondaryDirectoryName(DeleteInfo.getSecondaryDirectoryName());
            secondaryDirectory.setPrimaryDirectory(DeleteInfo.getPrimaryDirectory());

        }

        Info.put("meg", meg);

        return Info.toString();
    }

}
