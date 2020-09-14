package com.example.demo.dao;

import com.example.demo.mapper.userMapper;
import com.example.demo.pojo.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcelPutOutDao {

    @Autowired
    private userMapper userMapper;

    public SXSSFWorkbook ExportUserInfo() {
        String[] excelHeader = {"用户Id", "用户名", "手机号", "微信", "签名", "状态", "注册时间"};
        List<User> allinfo = userMapper.queryAll();

        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sheet = wb.createSheet("UserInfo");
        SXSSFRow row = sheet.createRow((int) 0);

        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(Font.COLOR_RED);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);


        for (int i = 0; i < excelHeader.length; i++) {
            SXSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, 100 * 70);
        }

        for(int i=0; i < allinfo.size(); i++) {

            User user = allinfo.get(i);
            row = sheet.createRow(i + 1);

            for(int j=0; j < excelHeader.length; j++){
                SXSSFCell cell = row.createCell(j);
                switch (j){
                    case 0:
                        cell.setCellValue(user.getId());
                        break;
                    case 1:
                        cell.setCellValue(user.getUserName());
                        break;
                    case 2:
                        cell.setCellValue(user.getUserPhone());
                        break;
                    case 3:
                        cell.setCellValue(user.getUserWeiXin());
                        break;
                    case 4:
                        cell.setCellValue(user.getUserAutograph());
                        break;
                    case 5:
                        cell.setCellValue(user.getUserState());
                        break;
                    default:
                        cell.setCellValue(user.getUserRegisterTime());
                        break;
                }
            }

        }

        return wb;
    }

}
