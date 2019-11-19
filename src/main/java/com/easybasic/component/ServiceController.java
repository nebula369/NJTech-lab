package com.easybasic.component;

import com.alibaba.fastjson.JSON;
import com.easybasic.basic.component.AppAuthHandler;
import com.easybasic.basic.model.ThirdApp;
import com.easybasic.basic.service.ThirdAppService;
import com.easybasic.component.Utils.DESUtil;
import com.easybasic.component.Utils.EncodeUtils;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.service.dto.ResponseCode;
import com.easybasic.service.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

public class ServiceController {

    @Resource
    protected ThirdAppService thirdAppService;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 验证工具服务调用请求有效性
     * @param param
     * @return
     */
    protected boolean validateRequest(MyDictionary param)
    {
        String ss = DESUtil.decode(param.get("sign"));
        MyDictionary dictionary = MyDictionary.Init(ss);
        if (dictionary.get("product").equalsIgnoreCase("easyds"))
        {
            Date dateTime = TypeConverter.stringToDate(dictionary.get("date"), "yyyy-MM-dd HH:mm:ss");
            long ts = new Date().getTime() - dateTime.getTime();
            long totalMinute = ts / (1000*60);
            if (totalMinute > 20)
            {
                //超过20分钟，无效
                return false;
            }
            return true;
        }
        return false;
    }

    public static String jsonSerializeObject(Object o)
    {
        String json = JSON.toJSONString(o);
        json = EncodeUtils.urlEncode(EncodeUtils.base64Encode(json));
        return json;
    }

    protected ResponseDTO checkThirdAppValidate(String appKey, int userId)
    {
        //第三方应用访问登录
        if(!AppAuthHandler.isHaveThirdAppAuth(appKey, userId))
        {
            return ResponseCode.buildResponseData(ResponseCode.USER_APP_NOT_AUTH, null);
        }
        ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
        if(thirdApp == null)
        {
            return ResponseCode.buildResponseData(ResponseCode.THIRDAPP_NOT_REGISTER, null);
        }
        if(thirdApp.getStatus() == 0)
        {
            return ResponseCode.buildResponseData(ResponseCode.THIRDAPP_NOT_ACTIVE, null);
        }
        return ResponseCode.buildResponseData(ResponseCode.RESPONSE_CODE_SUCCESS, null);
    }

    protected ResponseDTO checkThirdAppValidate(String appKey)
    {
        ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
        if(thirdApp == null)
        {
            return ResponseCode.buildResponseData(ResponseCode.THIRDAPP_NOT_REGISTER, null);
        }
        if(thirdApp.getStatus() == 0)
        {
            return ResponseCode.buildResponseData(ResponseCode.THIRDAPP_NOT_ACTIVE, null);
        }
        return ResponseCode.buildResponseData(ResponseCode.RESPONSE_CODE_SUCCESS, null);
    }
}
