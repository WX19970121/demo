package com.example.demo.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;

@Service
public class AliyunDao {

    private static final String RandomNumberInventory = "0123456789";

    public String GetPhoneRandomNum(int NumLength) {
        // TODO Auto-generated method stub
        StringBuilder RandomNum = new StringBuilder(""); //创建随机数对象

        for(int i = 0; i < NumLength; i++) {
            int Random = (int)(Math.random() * RandomNumberInventory.length());
            RandomNum.append(RandomNumberInventory.charAt(Random));
        }

        return RandomNum.toString(); //返回产生的随机数
    }

    //发送验证码
    public String SendMessToPhone(String PhoneNum, String Code) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GJXM9kx4dqKNbmAqhjh", "2kOcN806SSy4p4QMjN6LEUy4E67XYa");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", PhoneNum);
        request.putQueryParameter("SignName", "ABC应用APP");
        request.putQueryParameter("TemplateCode", "SMS_201470003");

        JSONObject JsonCode = new JSONObject();
        JsonCode.put("code", Code);

        request.putQueryParameter("TemplateParam", JsonCode.toJSONString());

        CommonResponse response = client.getCommonResponse(request);

        JSONObject Meg = JSON.parseObject(response.getData());

        return Meg.getString("Code");
    }

}
