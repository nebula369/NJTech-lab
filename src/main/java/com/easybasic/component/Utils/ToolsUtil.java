package com.easybasic.component.Utils;

import com.alibaba.druid.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.*;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class ToolsUtil {

    private static final Logger logger = LoggerFactory.getLogger(ToolsUtil.class);

    public static Calendar getCalendarInstance(Date date)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    private static String UploadPathPrex = PropertyUtil.getProperty("uploadPathPrex");
    /**
     * 获取上传文件目录
     * @param strPath
     * @return
     */
    public static String getUploadPath(String strPath)
    {
        String path = System.getProperty("catalina.home");
        path = path.replace("\\","/");
        if(!path.endsWith("/")) path = path +"/";
        strPath = strPath.replace("\\","/");
        if(strPath.startsWith("/")) strPath = strPath.substring(1);
        return path + UploadPathPrex + "/" + strPath;
    }

    /**
     * 获取WEB根目录
     * @param strPath
     * @return
     */
    public static String getWebRootPath(String strPath)
    {
        String basePath = System.getProperty("web.easybasic.root");
        basePath = basePath.replace("\\","/");
        if(strPath == null || strPath.length() == 0)
        {
            return basePath;
        }
        if(basePath.endsWith("/")) basePath = basePath.substring(0, basePath.length()-1);
        return basePath + strPath;
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    public static void deleteDirectory(String folder) throws IOException {
        File directory = new File(folder);
        if (!directory.exists()) {
            return;
        }

        cleanDirectory(directory);

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            }
            catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        }
        else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: "
                            + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    public static void deleteFile(String fileName){
        File file = new File(fileName);
        if (file.isDirectory()) {
            return;
        }
        else {
            if(file.exists())
            {
                file.delete();
            }
        }
    }

    public static long sizeOf(File file) {

        if (!file.exists()) {
            String message = file + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (file.isDirectory()) {
            return sizeOfDirectory(file);
        }
        else {
            return file.length();
        }

    }

    public static long sizeOfDirectory(File directory) {
        checkDirectory(directory);

        final File[] files = directory.listFiles();
        if (files == null) { // null if security restricted
            return 0L;
        }
        long size = 0;

        for (final File file : files) {

            size += sizeOf(file);
            if (size < 0) {
                break;

            }

        }

        return size;
    }

    private static void checkDirectory(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        }
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory
                    + " is not a directory");
        }
    }

    public static String formatNumber(Object num,String pattern)
    {
        DecimalFormat df1 = new DecimalFormat(pattern);
        return df1.format(num);
    }

    /**
     * 把一个字符串写到指定文件中
     * @param str  要写入文件中的字符串内容
     * @param filePath 文件夹路径
     */
    public static void writeStringToFile(String str,String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter fw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (filePath,false),"UTF-8"));
        fw.write(str);
        fw.flush();
        fw.close();
    }

    /**
     * 得到文件的扩展名
     * @param f
     * @return
     */
    public static String getFileExtension(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i).toLowerCase();
            }
        }
        return null;
    }

    public static String getFileExtensionWithNoDian(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i+1).toLowerCase();
            }
        }
        return null;
    }

    /**
     * 得到文件名(排除文件扩展名)
     * @param f
     * @return
     */
    public static String getFileNameWithoutExt(File f) {
        if (f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(0, i);
            }
        }
        return null;
    }

    public static String getFileName(String file)
    {
        String fname = file.trim();
        String fileName = fname.substring(fname.lastIndexOf("/")+1);
        return fileName;
    }

    /**
     * 改变文件的扩展名
     * @param fileNM
     * @param ext
     * @return
     */
    public static String changeFileExt(String fileNM, String ext) {
        int i = fileNM.lastIndexOf('.');
        if (i >= 0)
            return (fileNM.substring(0, i) + ext);
        else
            return fileNM;
    }

    /**
     * 判断目录是否存在
     * @param strDir
     * @return
     */
    public static boolean existsDirectory(String strDir) {
        File file = new File(strDir);
        return file.exists() && file.isDirectory();
    }

    /**
     * 判断文件是否存在
     * @param strDir
     * @return
     */
    public static boolean existsFile(String strDir) {
        File file = new File(strDir);
        return file.exists();
    }

    /**
     * 强制创建目录
     * @param strDir
     * @return
     */
    public static boolean forceDirectory(String strDir) {
        File file = new File(strDir);
        file.mkdirs();
        return existsDirectory(strDir);
    }

    /**
     * 得到文件的大小
     * @param fileName
     * @return
     */
    public static int getFileSize(String fileName){

        File file = new File(fileName);
        FileInputStream fis = null;
        int size = 0 ;
        try {
            fis = new FileInputStream(file);
            size = fis.available();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return size ;
    }

    public static String readFileContent(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static HttpServletResponse getResponse() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static String getRequestIP() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ip = inetAddress.getHostAddress();
        }
        return ip;
    }

    /**
     * 判断指定字符串在指定字符串数组中的位置
     * @param strSearch
     * @param stringArray
     * @param caseInsensetive
     * @return
     */
    public static int GetInArrayId(String strSearch, String[] stringArray, Boolean caseInsensetive)
    {
        for (int i = 0; i < stringArray.length; i++)
        {
            if (caseInsensetive)
            {
                if (strSearch.toLowerCase().equals(stringArray[i].toLowerCase()))
                    return i;
            }
            else if (strSearch.equals(stringArray[i]))
                return i;
        }
        return -1;
    }

    /**
     * 判断指定字符串在指定字符串数组中的位置
     * @param strSearch
     * @param stringArray
     * @return
     */
    public static int GetInArrayId(String strSearch, String[] stringArray)
    {
        return GetInArrayId(strSearch, stringArray, true);
    }

    /**
     * 判断指定字符串是否属于指定字符串数组中的一个元素
     * @param strSearch
     * @param stringArray
     * @param caseInsensetive
     * @return
     */
    public static Boolean InArray(String strSearch, String[] stringArray, Boolean caseInsensetive)
    {
        return GetInArrayId(strSearch, stringArray, caseInsensetive) >= 0;
    }

    /**
     * 判断指定字符串是否属于指定字符串数组中的一个元素
     * @param str
     * @param stringarray
     * @return
     */
    public static Boolean InArray(String str, String[] stringarray)
    {
        return InArray(str, stringarray, false);
    }

    /**
     * 判断指定字符串是否属于指定字符串数组中的一个元素
     * @param str
     * @param stringArray
     * @return
     */
    public static Boolean InArray(String str, String stringArray)
    {
        return InArray(str, SplitString(stringArray, ","), false);
    }

    /**
     * 判断指定字符串是否属于指定字符串数组中的一个元素
     * @param str
     * @param stringarray
     * @param strsplit
     * @return
     */
    public static Boolean InArray(String str, String stringarray, String strsplit)
    {
        return InArray(str, SplitString(stringarray, strsplit), false);
    }

    /**
     *  判断指定字符串是否属于指定字符串数组中的一个元素
     * @param str
     * @param stringarray
     * @param strsplit
     * @param caseInsensetive
     * @return
     */
    public static Boolean InArray(String str, String stringarray, String strsplit, Boolean caseInsensetive)
    {
        return InArray(str, SplitString(stringarray, strsplit), caseInsensetive);
    }

    /**
     *  分割字符串
     * @param strContent
     * @param strSplit
     * @return
     */
    public static String[] SplitString(String strContent, String strSplit)
    {
        if (!StringUtils.isEmpty(strContent))
        {
            if (strContent.indexOf(strSplit) < 0)
                return new String[] { strContent };
            Pattern pattern = Pattern.compile(strSplit, Pattern.CASE_INSENSITIVE);
            return pattern.split(strContent);
        }
        return new String[] { };
    }

    public static String getPhotoSrc(String photo)
    {
        String src = "";
        if (!StringUtils.isEmpty(photo))
        {
            Pattern pattern = Pattern.compile("src=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
            Matcher m = pattern.matcher(photo);
            m.find();
            src=  m.group(1);
            src = src.replace("./", "/");
        }
        return src;
    }

    public static String getFlag(int level, Boolean isLast)
    {
        String result = "";
        for (int i = 0; i < level - 1; i++)
        {
            result += "<div class=Flag1>&nbsp;</div>";
        }
        if (level != 0)
        {
            if (isLast)
            {
                result += "<div class=Flag3>&nbsp;</div>&nbsp;";
            }
            else
            {
                result += "<div class=Flag2>&nbsp;</div>&nbsp;";
            }
        }
        return result;
    }

    public static Boolean isSafeSqlString(String str)
    {
        Pattern pattern = Pattern.compile("create |insert|update|delete|exec|execute|alter|drop|substring|master|truncate|declare|xp_cmdshell|restore|backup|net +user|net +localgroup +administrators", Pattern.CASE_INSENSITIVE);
        Boolean result = !pattern.matcher(str).matches();
        return result;

    }

    public static void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }


    public static void copyFolderFileForCoverForNew(String oriPath, String targetPath) throws IOException
    {
        if (!ToolsUtil.existsDirectory(targetPath))
        {
            ToolsUtil.forceDirectory(targetPath);
        }
        File directoryInfo = new File(oriPath);
        File[] list = directoryInfo.listFiles();
        for (File fileInfo : list)
        {
            if(fileInfo.isDirectory()) continue;
            String ext =getFileExtension(fileInfo);
            if(ext == null) continue;
            if (ext.equalsIgnoreCase(".scc")) continue;
            copyFile(fileInfo, new File(targetPath + "/" + fileInfo.getName()));
        }
    }


    public static void copyFolderForCoverForNew(String oriPath, String targetPath) throws IOException
    {
        copyFolderFileForCoverForNew(oriPath, targetPath);
        if (!ToolsUtil.existsDirectory(targetPath))
        {
            ToolsUtil.forceDirectory(targetPath);
        }
        File directoryInfo = new File(oriPath);
        File[] directoryInfos = directoryInfo.listFiles();
        for (File info : directoryInfos)
        {
            if(!info.isDirectory())continue;
            copyFolderForCoverForNew(oriPath + "/" + info.getName(), targetPath + "/" + info.getName());
        }
    }

    public static String randomFileName(String fileName)
    {
        if(fileName==null)
            fileName="";

        fileName=getFileExtension(new File(fileName));
        fileName=TypeConverter.dateToString(new Date(),"yyyyMMddhhmmss")+ getCalendarInstance(new Date()).get(Calendar.MILLISECOND)+fileName;
        return fileName;
    }

    public static String formatTimeStrForVideo(int secends)
    {
        String result = "";
        if (secends == 0) return "";
        int minute = secends / 60;
        int sec = secends - minute * 60;
        int hour = minute / 60;
        minute = minute - hour * 60;
        if (hour == 0)
        {
            result = formatNumber(minute, "##")+ ":" + formatNumber(sec, "##");
        }
        else
        {
            result = formatNumber(hour, "##") + ":" + formatNumber(minute,"##") + ":" + formatNumber(sec,"##");
        }
        return result;
    }

    public static String cutString(String str, int startIndex, int length)
    {
        if (startIndex >= 0)
        {
            if (length < 0)
            {
                length = length * -1;
                if (startIndex - length < 0)
                {
                    length = startIndex;
                    startIndex = 0;
                }
                else
                    startIndex = startIndex - length;
            }

            if (startIndex > str.length())
                return "";
        }
        else
        {
            if (length < 0)
                return "";
            if (length + startIndex > 0)
            {
                length = length + startIndex;
                startIndex = 0;
            }
            else
                return "";
        }

        if (str.length() - startIndex < length)
            length = str.length() - startIndex;

        return str.substring(startIndex, length);
    }

    public static String getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);

    }

    public static String getRandomStr(int len)
    {
        String str1 = "";
        String[] s = {  "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        Random random = new Random();
        for (int i = 0; i < len; i++)
        {
            str1 = str1 + s[random.nextInt(s.length)];
        }
        return str1;
    }

    public static String getFileLastWriteTime(File f)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(f.lastModified());
        return sdf.format(cal.getTime());
    }

    public static String getHttpContent(String httpURl)
    {
        return getHttpContent(httpURl, "UTF-8");
    }

    public static String getHttpContent(String httpURl, String charsetName)
    {
        String result = "";
        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(httpURl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6* 1000);//设置连接超时
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();//得到网络返回的输入流
                if ("gzip".equals(conn.getContentEncoding())) {
                    result = readDataForZgip(is, charsetName);
                } else {
                    result = readData(is, charsetName);
                }
            }
        }
        catch (Exception ex)
        {

        }
        finally {

            if(conn != null)
            {
                conn.disconnect();
            }
        }
        return result;
    }

    private static String readData(InputStream inStream, String charsetName) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len = inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return new String(data, charsetName);
    }

    //第一个参数为输入流,第二个参数为字符集编码
    private static String readDataForZgip(InputStream inStream, String charsetName) throws Exception{
        GZIPInputStream gzipStream = new GZIPInputStream(inStream);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer =new  byte[1024];
        int len = -1;
        while ((len = gzipStream.read(buffer))!=-1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        gzipStream.close();
        inStream.close();
        return new String(data, charsetName);
    }

    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."+
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."+
            "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }

    public static String getServerIp(){
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String hostAddress = address.getHostAddress();
        return hostAddress;
    }

    // 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    // 将十进制整数形式转换成127.0.0.1形式的ip地址
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

}
