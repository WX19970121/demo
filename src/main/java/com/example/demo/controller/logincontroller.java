package com.example.demo.controller;

import com.example.demo.dao.YZMCode;
import com.example.demo.dao.loginDao;
import com.example.demo.pojo.AdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class logincontroller {

    @Autowired
    private YZMCode yzmCode;

    @Autowired
    private loginDao loginDao;

    @RequestMapping("/openpage")
    public ModelAndView OpenLoginPage(ModelAndView modelAndView){
        modelAndView.addObject("username", "");
        modelAndView.addObject("password", "");
        modelAndView.setViewName("login/login");
        return modelAndView;
    }

    @RequestMapping("/loginaction")
    public ModelAndView loginaction(@RequestParam("username")String username,
                              @RequestParam("password")String password,
                              @RequestParam("enCode")String enCode,
                              ModelAndView modelAndView,
                              HttpServletRequest request){
        String Code = (String) request.getSession().getAttribute("YZMCode");

        System.out.println(username);
        System.out.println(password);

        if(!Code.toLowerCase().equals(enCode.toLowerCase())){
            modelAndView.addObject("username", username);
            modelAndView.addObject("password", password);
            modelAndView.setViewName("login/login");
        }
        else{
            AdminUser queryLoginAdmin = loginDao.queryAdminUserByAdminName(username);
            if(queryLoginAdmin == null){
                modelAndView.addObject("username", username);
                modelAndView.addObject("password", password);
                modelAndView.setViewName("login/login");
            }
            else{
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(queryLoginAdmin.getAdminName(), password);
                try {
                    subject.login(usernamePasswordToken);
                }catch (Exception e){
                    e.printStackTrace();
                    subject.logout();

                    modelAndView.addObject("username", username);
                    modelAndView.addObject("password", password);
                    modelAndView.setViewName("login/login");

                    return modelAndView;
                }

                if(subject.isAuthenticated()){
                    modelAndView.setViewName("redirect:/mainpage/openpage");
                }
            }
        }

        return modelAndView;
    }

    @RequestMapping("/getYZM")
    public void GetYZM(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String YZMCode = yzmCode.GetRandomNum(4);
        request.getSession().setAttribute("YZMCode", YZMCode);
        BufferedImage bufferedImage = yzmCode.SetVerificationCode(YZMCode);
        ImageIO.write(bufferedImage, "JPG", response.getOutputStream());
    }

    @RequestMapping("/cancellationAdminService")
    public ModelAndView CanceAdmin(ModelAndView modelAndView, HttpServletRequest request){
        request.getSession().removeAttribute("adminUser");
        modelAndView.setViewName("redirect:/login/openpage");
        return modelAndView;
    }

}
