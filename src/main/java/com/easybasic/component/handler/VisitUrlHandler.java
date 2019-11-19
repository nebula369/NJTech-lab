package com.easybasic.component.handler;

import com.easybasic.component.Utils.PropertyUtil;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.PropertyUtil;
import com.easybasic.component.Utils.ToolsUtil;

public class VisitUrlHandler {

    private static String IntranetSegs = PropertyUtil.getProperty("intranetSegs");

    /**
     * 检查客户端访问IP是外网还是内网访问：true为外网访问，false为内网访问
     * @return
     */
    public static boolean checkClientVisitIP()
    {
        String url = ToolsUtil.getRequestIP();
        return checkClientVisitIP(url);
    }

    public static boolean checkClientVisitIP(String url)
    {
        boolean result = true;
        String ipStr = url.toLowerCase();
        if (ipStr.startsWith("http://")) ipStr = ipStr.replace("http://", "");
        if (ipStr.endsWith("/")) ipStr = ipStr.substring(0, ipStr.length() - 1);
        if (ipStr.indexOf(':') > 0) ipStr = ipStr.substring(0, ipStr.indexOf(':'));
        boolean isIP = ToolsUtil.ipCheck(ipStr);
        //如果是域名，则判为外网
        if (isIP)
        {
            String a = ipStr.substring(0, ipStr.indexOf("."));
            if (ToolsUtil.InArray(a, IntranetSegs, ","))
            {
                result = false;
            }
        }
        return result;
    }
}
