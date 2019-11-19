package com.easybasic.component.Utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonUtils {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    /**
     * Json转对象
     * @param clazz 对象类
     * @param json json字符串
     * @param <T> T
     * @return
     */
    public static <T> T parseFromJson(Class<T> clazz, String json) {
        try {
            //创建解析XML对象
            if(StringUtils.isEmpty(json)) return null;
            T t = JSON.parseObject(json, clazz);
            return t;
        }
        catch (Exception ex)
        {
            logger.error("json转对象异常", ex);
            return null;
        }
    }

    /**
     * 对象转Json
     * @param obj 对象
     * @return
     */
    public static String toJson(Object obj) {
        try {
            String json = JSON.toJSONString(obj);
            return json;
        }
        catch (Exception ex)
        {
            logger.error("对象转json异常", ex);
            return "";
        }
    }

    public static void save(Object obj, String fileName) throws IOException
    {
        try {
            String json = toJson(obj);
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, false);
            fw.write(json);
            fw.flush();
            fw.close();
        }
        catch (Exception ex)
        {
            logger.error("序列化object为JSON并保存到文件“"+fileName+"”异常", ex);
        }
    }

    public static <T> T load(Class<T> clazz, String fileName) {
        try {
            String xml = ToolsUtil.readFileContent(fileName);
            return parseFromJson(clazz, xml);
        }
        catch (Exception ex)
        {
            logger.error("反序化json文件“"+fileName+"”异常",ex);
            return null;
        }
    }

}
