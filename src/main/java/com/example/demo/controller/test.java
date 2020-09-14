package com.example.demo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.word.WordExportUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.mapper.chartstitleMapper;
import com.example.demo.mapper.chartsvalueMapper;
import com.example.demo.mapper.primaryDirectoryMapper;
import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.ChartsTitle;
import com.example.demo.pojo.ChartsValue;
import com.example.demo.pojo.PrimaryDirectory;
import com.example.demo.pojo.User;
import com.google.gson.JsonObject;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/Test")
public class test {

    @Autowired
    private userMapper userMapper;

    @Autowired
    private primaryDirectoryMapper primaryDirectoryMapper;

    @Autowired
    private chartstitleMapper chartstitleMapper;

    @Autowired
    private chartsvalueMapper chartsvalueMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/ASD")
    public void Test(HttpServletResponse response) throws IOException {
        List<User> A = userMapper.queryAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("目录信息","目录"),
                User.class, A);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=Test.xls");

        OutputStream ouputStream = response.getOutputStream();

        workbook.write(ouputStream);
        workbook.close();
    }

    @RequestMapping("/ASB")
    public void World(HttpServletResponse response) throws Exception {

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-disposition", "attachment;filename=Test.docx");
        OutputStream ouputStream = response.getOutputStream();

        TemplateExportParams params = new TemplateExportParams(
                "src/main/java/com/example/demo/Temple/MyTest.xls");

        Map<String, Object> map = new HashMap<String, Object>();

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("id", i + 1 + "");
            lm.put("name", "Name" + i);
            lm.put("age", "20" + i);
            lm.put("mess", "Name" + i);

            listMap.add(lm);
        }

        map.put("wuxin", listMap);
        map.put("Sum", 1);


        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        XWPFDocument doc = WordExportUtil.exportWord07(
                "src/main/java/com/example/demo/Temple/Test.docx", map);


        doc.write(ouputStream);
        doc.close();
    }

    @RequestMapping("/GoChartsPage")
    public String Gopage(){
        return "main/Charts";
    }

    @RequestMapping("/AddChartsInfo")
    @ResponseBody
    public String AddChartsInfo(ChartsValue chartsValueList, HttpServletRequest request){
        JSONObject B = new JSONObject();

        long titleID = Long.parseLong((String) request.getParameter("titleId"));

        ChartsTitle title = chartstitleMapper.queryByChartsTitleId(titleID);
        chartsValueList.setChartsTitle(title);

        chartsvalueMapper.save(chartsValueList);

        B.put("A", "D");
        return B.toString();
    }

    @RequestMapping("/TestRedis")
    @ResponseBody
    public String TestRedis(){
        redisTemplate.delete("Name");
        return (String) redisTemplate.opsForValue().get("Name");
    }

}
