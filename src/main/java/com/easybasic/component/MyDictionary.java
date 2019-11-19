package com.easybasic.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easybasic.component.Utils.EncodeUtils;
import org.apache.commons.lang.StringUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MyDictionary {

    protected Map<String, Object> paramMap;

    public MyDictionary(Map<String, Object> p)
    {
        paramMap = p;
    }

    public MyDictionary()
    {
        paramMap = new HashMap<>();
    }

    public String get(String paramKey)
    {
        if (paramMap.containsKey(paramKey))
        {
            if (paramMap.get(paramKey) == null)
            {
                return "";
            }
            return paramMap.get(paramKey).toString();
        }
        paramKey = paramKey.toLowerCase().trim();
        if (paramMap.containsKey(paramKey))
        {
            if (paramMap.get(paramKey) == null)
            {
                return "";
            }
            return paramMap.get(paramKey).toString();
        }
        return "";
    }

    public void put(String paramKey, String paramValue)
    {
        paramMap.put(paramKey, paramValue);
    }

    public Boolean containsKey(String key)
    {
        return paramMap.containsKey(key);
    }

    public Set<String> getKeys()
    {
       return paramMap.keySet();
    }

    public String toJsonParams()
    {
        String json = JSON.toJSONString(paramMap);
        json = EncodeUtils.base64Encode(json);
        return json;
    }

    public static MyDictionary Init(String parms)
    {
        Map<String, Object> p = new HashMap<>();
        if (!StringUtils.isEmpty(parms))
        {
            String param = EncodeUtils.base64Decode(parms);
            p = JSONObject.parseObject(param);
        }
        MyDictionary myDictionary = new MyDictionary(p);
        return myDictionary;
    }
}
