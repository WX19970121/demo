package com.example.demo.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OtherDao {

    public String GetNowTime() {
        // TODO Auto-generated method stub
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    public int GetPagNum(int total, int limit){
        int PageNum = 0;
        if(total <= limit){
            PageNum = 1;
        }
        else {
            if(total % limit == 0){
                PageNum = total / limit;
            }
            else {
                PageNum = total / limit + 1;
            }
        }

        return PageNum;
    }

    //过滤Json数据
    public JSONArray FilterJSONArrar(String[] FilterInfo, List<?> ListInfo) {
        SimplePropertyPreFilter Filter = new SimplePropertyPreFilter();
        for(int i = 0; i < FilterInfo.length; i++){
            Filter.getExcludes().add(FilterInfo[i]);
        }

        String ArrayInfo = JSON.toJSONString(ListInfo, Filter);

        return JSON.parseArray(ArrayInfo);
    }

    public JSONObject FilterJSONObject(String[] FilterInfo, Object ObjectInfo){
        SimplePropertyPreFilter Filter = new SimplePropertyPreFilter();
        for(int i = 0; i < FilterInfo.length; i++){
            Filter.getExcludes().add(FilterInfo[i]);
        }

        String JSONObjectInfo = JSON.toJSONString(ObjectInfo, Filter);

        return JSON.parseObject(JSONObjectInfo);
    }

}
