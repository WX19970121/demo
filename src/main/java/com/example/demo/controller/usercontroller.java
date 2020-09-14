package com.example.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dao.OtherDao;
import com.example.demo.dao.UploadDao;
import com.example.demo.dao.YZMCode;
import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.User;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user-manager")
public class usercontroller {

    @Autowired
    private UploadDao uploadDao;

    @Autowired
    private YZMCode yzmCode;

    @Autowired
    private OtherDao otherDao;

    @Autowired
    private userMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;


    @RequestMapping("/GoInfoPage")
    public ModelAndView EnterInfoPage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/user-manager");
        return modelAndView;
    }

    @RequestMapping("/GoAddPage")
    public ModelAndView EnterAddPage(ModelAndView modelAndView){
        modelAndView.setViewName("admin/user-manager-add");
        return modelAndView;
    }

    @RequestMapping("/uploadheadimg")
    @ResponseBody
    public String UploadImg(@RequestParam(value="file") MultipartFile file, HttpServletRequest request) throws IOException {
        JSONObject meg = new JSONObject();

        String webPath = request.getServletContext().getRealPath("/");

        //删除之前上传的图片以节省空间
        String oldImgName = (String) request.getSession().getAttribute("oldimgName");
        System.out.println(oldImgName);
        if(oldImgName != null && !"".equals(oldImgName)){
            File OldImgPath = new File(webPath + "img/templepath/" + oldImgName);
            OldImgPath.delete();
        }

        int length = (int)(Math.random() * 49 + 1);
        String FileView = yzmCode.GetRandomNum(length);
        String Stuffix = uploadDao.GetFieStuffix(file.getOriginalFilename());
        String NewFileName = FileView + Stuffix;

        file.transferTo(new File(webPath + "img/templepath/" + NewFileName));

        request.getSession().setAttribute("oldimgName", NewFileName);

        meg.put("code", 0);
        meg.put("imgName", NewFileName);
        return meg.toString();
    }

    @RequestMapping("/getTableInfo")
    @ResponseBody
    public String SetInfo(HttpServletRequest request){
        int page = Integer.parseInt((String) request.getParameter("page"));
        int rows = Integer.parseInt((String) request.getParameter("rows"));

        List<User> querylist = new ArrayList<User>();

        ValueOperations valueOperations = redisTemplate.opsForValue();

        if(page == 1 || querylist == null){
            redisTemplate.delete("AllUserInfo");
        }

        if(redisTemplate.hasKey("AllUserInfo")){
            querylist = (List<User>) valueOperations.get("AllUserInfo");
        }
        else {
            querylist = userMapper.queryAll();
            valueOperations.set("AllUserInfo", querylist);
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

    @RequestMapping("/edit")
    @ResponseBody
    public String UserEdit(HttpServletRequest request, User user) throws IOException {
        String edit = (String) request.getParameter("edit");
        JSONObject info = new JSONObject();

        if("add".equals(edit)){
            User check = userMapper.queryByUserPhone(user.getUserPhone());
            if(check == null){

                //将临时文件移动到正式文件
                String webPath = request.getServletContext().getRealPath("/");
                File ImgPath = new File(webPath + "img/templepath/" + user.getUserHeadImg());
                FileUtils.copyFile(ImgPath, new File(webPath + "img/formalpath/" + user.getUserHeadImg()));

                user.setUserState("正常");
                user.setUserRegisterTime(otherDao.GetNowTime());
                user.setUserHeadImgPath(webPath + "img/formalpath/" + user.getUserHeadImg());

                userMapper.save(user);

                //移动成功后删除临时文件夹的文件
                ImgPath.delete();

                info.put("meg", "ADD SUCCESS");


            }
            else{
                info.put("meg", "USER EXISTENCE");
            }
        }

        if("editstatus".equals(edit)){
            User ChangeInfo = userMapper.queryById(user.getId());
            ChangeInfo.setUserState(user.getUserState());
            userMapper.saveAndFlush(ChangeInfo);
            info.put("meg", "CHANGE STATUS SUCCESS");

            user.setUserPhone(ChangeInfo.getUserPhone());
        }

        if("delete".equals(edit)){
            User deleteInfo = userMapper.queryById(user.getId());
            userMapper.delete(deleteInfo);

            //删除用户的头像
            String webPath = request.getServletContext().getRealPath("/");
            File DeleteImgPath = new File(webPath + "img/formalpath/" + deleteInfo.getUserHeadImg());
            DeleteImgPath.delete();

            info.put("meg", "DELETE SUCCESS");

            user.setUserPhone(deleteInfo.getUserPhone());
        }

        return info.toString();
    }

}
