package com.easybasic.resourceget;

import com.easybasic.component.MyDictionary;
import com.easybasic.component.Utils.EncodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取资源数据能用方法
 */
public class ResourceGetHandler {

    private static Logger logger = LoggerFactory.getLogger(ResourceGetHandler.class);

    /**
     * 通过post方式获取接口数据
     * @param serviceUrl
     * @param param
     * @return
     */
    public static String getHttpWebResponseForPost(String serviceUrl, String param) {
        try {
            String postData = param;
            URL url = new URL(serviceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true); //获取返回数据需要设置为true 默认false
            con.setDoInput(true); //发送数据需要设置为true 默认false
            con.setReadTimeout(50000);
            con.setConnectTimeout(50000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.connect();
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            if (param != null) {
                out.writeBytes(new String(postData.getBytes("utf-8")));
            }
            out.flush();
            out.close();
            BufferedReader red = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = red.readLine()) != null) {
                sb.append(line);
            }
            red.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用服务“"+serviceUrl+"”错误", e);
            return null;
        }
    }

    public static String getHttpWebResponseForPost(String serviceUrl, MyDictionary param) {
        try {
            String postData = "data=" + EncodeUtils.urlEncode(param.toJsonParams());
            URL url = new URL(serviceUrl + "?" + postData);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true); //获取返回数据需要设置为true 默认false
            con.setDoInput(true); //发送数据需要设置为true 默认false
            con.setReadTimeout(50000);
            con.setConnectTimeout(50000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.connect();
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            if (param != null) {
                out.writeBytes(new String(postData.getBytes("utf-8")));
            }
            out.flush();
            out.close();
            BufferedReader red = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = red.readLine()) != null) {
                sb.append(line);
            }
            red.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用服务“" + serviceUrl + "”错误", e);
            return null;
        }
    }

}
