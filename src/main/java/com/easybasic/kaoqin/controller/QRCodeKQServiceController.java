package com.easybasic.kaoqin.controller;

import com.alibaba.fastjson.JSONObject;
import com.easybasic.component.ServiceController;
import com.easybasic.kaoqin.model.PlanWeek;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QRCodeKQServiceController extends ServiceController {

    @RequestMapping("/wechatCheck")
    @ResponseBody
    public void auth(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String echostr = request.getParameter("echostr");
        if (echostr == null) {
            String appId = "wx27e277c43f298d2d";
            String appSecret = "e9435e29fe7d1339cca5ec2f1db96cb2";
            //拼接
            String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                    + "appid="
                    + appId
                    + "&secret="
                    + appSecret
                    + "&code=CODE&grant_type=authorization_code";
            String get_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String code = request.getParameter("code");
            /*System.out.println("******************code=" + code);*/
            get_access_token_url = get_access_token_url.replace("CODE", code);
            String json = doHttpsGetJson(get_access_token_url);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");
            get_userinfo = get_userinfo.replace("ACCESS_TOKEN", access_token);
            get_userinfo = get_userinfo.replace("OPENID", openid);
            String userInfoJson = doHttpsGetJson(get_userinfo);
            JSONObject userInfoJO = JSONObject.parseObject(userInfoJson);
            String user_openid = userInfoJO.getString("openid");
            String user_nickname = userInfoJO.getString("nickname");
            String user_sex = userInfoJO.getString("sex");
            String user_province = userInfoJO.getString("province");
            String user_city = userInfoJO.getString("city");
            String user_country = userInfoJO.getString("country");
            String user_headimgurl = userInfoJO.getString("headimgurl");
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
            out.println("<HTML>");
            out.println(" <HEAD><TITLE>A Servlet</TITLE></HEAD>");
            out.println(" <BODY>");
            out.print(" This is ");
            out.print(this.getClass());
            out.println(", using the POST method \n");
            out.println("openid:" + user_openid + "\n\n");
            out.println("nickname:" + user_nickname + "\n\n");
            out.println("sex:" + user_sex + "\n\n");
            out.println("province:" + user_province + "\n\n");
            out.println("city:" + user_city + "\n\n");
            out.println("country:" + user_country + "\n\n");
            out.println("<img src=/" + user_headimgurl + "/");
            out.println(">");
            out.println(" </BODY>");
            out.println("</HTML>");
            out.flush();
            out.close();
        } else {
            PrintWriter out = response.getWriter();
            out.print(echostr);
        }
    }

    public static String doHttpsGetJson(String Url)
    {
        String message = "";
        try
        {
            System.out.println("doHttpsGetJson");//TODO:dd
            URL urlGet = new URL(Url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");   //必须是get方式请求  24
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");//连接超时30秒28
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); //读取超时30秒29 30
            http.connect();
            InputStream is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            message=new String(jsonBytes,"UTF-8");
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return message;
    }

}