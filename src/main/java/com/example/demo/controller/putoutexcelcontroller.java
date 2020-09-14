package com.example.demo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.demo.dao.ExcelPutOutDao;
import com.example.demo.mapper.JournalMapper;
import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.Journal;
import com.example.demo.pojo.User;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/PutOutExcel")
public class putoutexcelcontroller {

    private SXSSFWorkbook sxssfWorkbook = null;

    @Autowired
    private userMapper userMapper;

    @Autowired
    private JournalMapper journalMapper;

    @RequestMapping("/ExcelUserPutOut")
    public void ExportUserInfo(HttpServletResponse response) throws IOException {
        List<User> ExcelInfo = userMapper.queryAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("目录信息","目录"),
                User.class, ExcelInfo);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=UserInfo.xls");

        OutputStream ouputStream = response.getOutputStream();

        workbook.write(ouputStream);
        workbook.close();
    }

    @RequestMapping("/ExcelJournalPutOut")
    public void ExportJournalInfo(HttpServletResponse response) throws IOException{
        List<Journal> ExcelInfo = journalMapper.queryAll();

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("日志","日志记录"),
                Journal.class, ExcelInfo);

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=Journal.xls");

        OutputStream ouputStream = response.getOutputStream();

        workbook.write(ouputStream);
        workbook.close();
    }

}
