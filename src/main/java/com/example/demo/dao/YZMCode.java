package com.example.demo.dao;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class YZMCode {

    private static final String RandomNumberInventory = "ABCDEFGHIJKLMNOPQRESTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    public String GetRandomNum(int NumLength) {
        // TODO Auto-generated method stub
        StringBuilder RandomNum = new StringBuilder(""); //创建随机数对象

        for(int i = 0; i < NumLength; i++) {
            int Random = (int)(Math.random() * RandomNumberInventory.length());
            RandomNum.append(RandomNumberInventory.charAt(Random));
        }

        return RandomNum.toString(); //返回产生的随机数
    }

    public BufferedImage SetVerificationCode(String VerificationCode) {
        // TODO Auto-generated method stub

        Random random = new Random(); //随机数对象

        int ImgWidth = 160; //图片长度
        int ImgHeight = 40; //图片宽度

        Font font = new Font("Fixedsys", Font.BOLD, 35); //字体大小

        BufferedImage buffImg = new BufferedImage(ImgWidth, ImgHeight, BufferedImage.TYPE_INT_RGB); //图片缓存流

        Graphics gd = buffImg.getGraphics(); //图片编辑对象

        gd.setFont(font); //设置验证码字体

        gd.setColor(Color.WHITE); //图片背景设置灰色
        gd.fillRect(0, 0, ImgWidth, ImgHeight); //将背景涂满整个图片

        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, ImgWidth - 1, ImgHeight - 1); //设置图片外框

        //在图片画干扰线
        for (int i = 0; i < 5; i++) {
            int x = random.nextInt(ImgWidth);
            int y = random.nextInt(ImgHeight);
            int xl = random.nextInt(28);
            int yl = random.nextInt(28);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        int red = 0, green = 0, blue = 0; //设定字体颜色

        //在图片画出验证码
        for (int i = 0; i < VerificationCode.length(); i++) {
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            gd.setColor(new Color(red, green, blue));

            String Code = VerificationCode.charAt(i) + "";

            gd.drawString(Code, 10+i*35, 32);
        }

        return buffImg;
    }

}
