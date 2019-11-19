package com.easybasic.component.Utils;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.component.model.XStreamDateConverter;import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

public class XmlUtils {

    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);
    /**
     * XML转对象
     * @param clazz 对象类
     * @param xml xml字符串
     * @param <T> T
     * @return
     */
    public static <T> T parseFromXml(Class<T> clazz, String xml, Class tclazz) {
        try {
            //创建解析XML对象
            if(StringUtils.isEmpty(xml)) return null;
            XStream xStream = new XStream(new DomDriver());
            xStream.autodetectAnnotations(true);
            //处理注解
            xStream.processAnnotations(clazz);
            xStream.registerConverter(new XStreamDateConverter());
            if(tclazz!=null)
            {
                xStream.alias(tclazz.getSimpleName(), tclazz);
                xStream.alias("ArrayOf" + tclazz.getSimpleName(), List.class);
            }
            @SuppressWarnings("unchecked")
            //将XML字符串转为bean对象
                    T t = (T) xStream.fromXML(xml);
            return t;
        }
        catch (Exception ex)
        {
            logger.error("xml转对象异常", ex);
            return null;
        }
    }

    /**
     * 对象转xml
     * @param obj 对象
     * @return
     */
    public static String toXml(Object obj, Class clazz) {
        try {
            XStream xStream = new XStream(new DomDriver());
            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(obj.getClass());
            xStream.registerConverter(new XStreamDateConverter());
            if(clazz!=null)
            {
                xStream.alias(clazz.getSimpleName(), clazz);
                xStream.alias("ArrayOf" + clazz.getSimpleName(), List.class);
            }
            return xStream.toXML(obj);
        }
        catch (Exception ex)
        {
            logger.error("对象转xml异常", ex);
            return "";
        }
    }

    public static void save(Object obj, String fileName, Class clazz) throws IOException
    {
        try {
            String xml = toXml(obj, clazz);
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter fw = new BufferedWriter (new OutputStreamWriter(new FileOutputStream (file,false),"UTF-8"));
            //FileWriter fw = new FileWriter(file, false);
            fw.write(xml);
            fw.flush();
            fw.close();
        }
        catch (Exception ex)
        {
            logger.error("序列化object并保存到文件“"+fileName+"”异常", ex);
        }
    }

    public static <T> T load(Class<T> clazz, String fileName, Class tclazz) {
        try {
            String xml = ToolsUtil.readFileContent(fileName);
            return parseFromXml(clazz, xml, tclazz);
        }
        catch (Exception ex)
        {
            logger.error("反序化xml文件“"+fileName+"”异常",ex);
            return null;
        }
    }
}
