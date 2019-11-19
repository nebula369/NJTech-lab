package com.easybasic.component.Utils;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TypeConverter {

    private static final Logger logger = LoggerFactory.getLogger(TypeConverter.class);
    /// <summary>
    /// 将对象转换为Int32类型
    /// </summary>
    /// <returns>转换后的int类型结果</returns>
    public static int objectToInt(Object expression)
    {
        if (expression == null)return 0;
        return objectToInt(expression, 0);
    }

    /// <summary>
    /// 将对象转换为Int32类型
    /// </summary>
    /// <param name="expression"></param>
    /// <param name="defValue">缺省值</param>
    /// <returns>转换后的int类型结果</returns>
    public static int objectToInt(Object expression, int defValue)
    {
        if (expression != null)
            return strToInt(expression.toString(), defValue);

        return defValue;
    }

    /// <summary>
    /// 将对象转换为Int32类型,转换失败返回0
    /// </summary>
    /// <param name="str">要转换的字符串</param>
    /// <returns>转换后的int类型结果</returns>
    public static int strToInt(String str)
    {
        return strToInt(str, 0);
    }

    /// <summary>
    /// 将对象转换为Int32类型
    /// </summary>
    /// <param name="str">要转换的字符串</param>
    /// <param name="defValue">缺省值</param>
    /// <returns>转换后的int类型结果</returns>
    public static int strToInt(String str, int defValue)
    {
        if(StringUtils.isEmpty(str)) return defValue;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        Matcher isNum = pattern.matcher(str);
        if (StringUtils.isEmpty(str) || str.trim().length() >= 11 || !isNum.matches()) {
            return defValue;
        }
        try
        {
            return Integer.parseInt(str);
        }
        catch(Exception ex) {
            return defValue;
        }
    }

    /// <summary>
    /// string型转换为float型
    /// </summary>
    /// <param name="strValue">要转换的字符串</param>
    /// <param name="defValue">缺省值</param>
    /// <returns>转换后的int类型结果</returns>
    public static float strToFloat(Object strValue, float defValue)
    {
        if ((strValue == null))
            return defValue;

        return strToFloat(strValue.toString(), defValue);
    }

    /// <summary>
    /// string型转换为float型
    /// </summary>
    /// <param name="strValue">要转换的字符串</param>
    /// <param name="defValue">缺省值</param>
    /// <returns>转换后的int类型结果</returns>
    public static float objectToFloat(Object strValue, float defValue)
    {
        if ((strValue == null))
            return defValue;

        return strToFloat(strValue.toString(), defValue);
    }

    /// <summary>
    /// string型转换为float型
    /// </summary>
    /// <param name="strValue">要转换的字符串</param>
    /// <returns>转换后的int类型结果</returns>
    public static float objectToFloat(Object strValue)
    {
        return objectToFloat(strValue.toString(), 0);
    }

    /// <summary>
    /// string型转换为float型
    /// </summary>
    /// <param name="strValue">要转换的字符串</param>
    /// <returns>转换后的int类型结果</returns>
    public static float strToFloat(Object strValue)
    {
        if ((strValue == null))
            return 0;

        return strToFloat(strValue.toString(), 0);
    }

    /// <summary>
    /// string型转换为float型
    /// </summary>
    /// <param name="strValue">要转换的字符串</param>
    /// <param name="defValue">缺省值</param>
    /// <returns>转换后的int类型结果</returns>
    public static float strToFloat(String strValue, float defValue)
    {
        if ((strValue == null))
            return defValue;

        float intValue = defValue;

        Pattern pattern = Pattern.compile("^([-]|[0-9])[0-9]*(\\.\\w*)?$");
        Matcher isFloat = pattern.matcher(strValue);
        if (isFloat.matches()) {
            try {
                return Float.parseFloat(strValue);
            }
            catch (Exception ex)
            {
                return defValue;
            }
        }
        return intValue;
    }

    public static Date stringToDate(String str, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(str);
        }
        catch (ParseException e) {

        }
        return date;
    }

    /**
     * Date 转 String
     * 2015年3月25日上午11:28:14
     * @param date 日期
     * @param format 转换格式
     * @return
     */
    public static String dateToString(Date date,String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String strDate=null;
        try {
            if(date!=null){
                strDate=dateFormat.format(date);
            }
        } catch (Exception e) {
            logger.error("Date类型 转 String类型出错："+e);
        }
        return strDate;
    }

    /**
     * 秒转换为指定格式的日期
     * @param second
     * @param patten
     * @return
     */
    public static String secondToDate(long second,String patten) {
        int hour = (int) (second / 3600);
        int minute = (int)(second % 3600 / 60);
        int se = (int)(second%60);
        return String.format("%02d", hour) + ":"+String.format("%02d", minute)+":"+String.format("%02d", se);
    }

}
