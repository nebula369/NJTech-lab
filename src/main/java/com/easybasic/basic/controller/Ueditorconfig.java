package com.easybasic.basic.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ueditor.ActionEnter;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
@RequestMapping("/manage/basic")
public class Ueditorconfig extends BaseController {
    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("ueconfig")
    public void  getUEConfig(HttpServletRequest request, HttpServletResponse response) {

        // 读取文件数据
        //System.out.println("读取文件数据util");
        String path = request.getSession().getServletContext().getRealPath("/");
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(path + "/assets/lib/js/ueditor/jsp/config.json");//"D:"+File.separatorChar+"DStores.json"
        if (!myFile.exists()) {
            //System.err.println("Can't Find " + pactFile);
        }
        try {
            FileInputStream fis = new FileInputStream(path + "/assets/lib/js/ueditor/jsp/config.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str);  //new String(str,"UTF-8")
            }
            in.close();
            PrintWriter out = response.getWriter();
            out.print(strbuffer.toString());
        } catch (IOException e) {
            e.getStackTrace();
        }


        /*String rootPath = request.getSession().getServletContext().getRealPath("/");
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String path = "//" + formater.format(new Date());
        String action = request.getParameter("action");
        String rPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/admin/ueditor/jsp";
        String sPath =  rootPath + "/upload/eclassbrand/news/";
        if(action.equals("uploadimage")){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            MultipartFile multipartFile = multipartHttpServletRequest.getFile("upfile");
            String filePath = sPath+path;
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            String imgName = multipartFile.getOriginalFilename();
            String hz = imgName.substring(imgName.indexOf("."));
            String uuid = UUID.randomUUID().toString();
            String uuid1 = UUID.randomUUID().toString();
            uuid = uuid.replace("-","");
            uuid1 = uuid1.replace("-","");
            String name = filePath+"//"+uuid+hz;
            String fileName = filePath+"//"+uuid1+hz;
            File f = new File(name);
            File f1 = new File(fileName);
            try {
                multipartFile.transferTo(f);
                InputStream inputStream = new FileInputStream(f);
                OutputStream os = new FileOutputStream(f1);
                resizeImage(inputStream,os,500,750,hz.replace(".",""));
                f.delete();
            }catch (Exception e){
                e.printStackTrace();
            }

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("original",multipartFile.getOriginalFilename());
            map.put("name",multipartFile.getOriginalFilename());
            map.put("url","/upload/eclassbrand/news"+"/"+uuid1+hz);
            map.put("size",multipartFile.getSize());
            map.put("type","."+hz);
            map.put("state","SUCCESS");
            return map;
        }else{
            response.setContentType("application/json");
            try {
                response.setCharacterEncoding("utf-8");
                request.setCharacterEncoding( "utf-8" );
                PrintWriter writer = response.getWriter();
                writer.write(new ActionEnter(request,rPath).exec());
                writer.flush();
                writer.close();
            }catch (Exception r){
                r.printStackTrace();
            }
        }
        return null;*/
    }

    /**
     * @Title: uploadUEditorImage
     * @Description: 自定义编辑器上传路径
     * @param @param file
     * @param @param response
     * @param @param request
     * @param @throws Exception
     * @return void
     * @date createTime：2018年4月9日下午1:52:19
     */
    @RequestMapping(value = "/ueditorUpload")
    public void ueditorUpload(@RequestParam(value = "upfile", required = false) MultipartFile file,
                              HttpServletResponse response, HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        JSONObject json = new JSONObject();
        PrintWriter out = response.getWriter();
        try {
            //要上传的磁盘路径String path = ToolsUtil.getUploadPath("/"+filepath);
            String rootPath = ToolsUtil.getUploadPath("/" + "upload/eclassbrand/news");
            //文件名
            String fileName = file.getOriginalFilename();
            //截取文件类型
            String fileType = fileName.substring(fileName.indexOf(".") + 1);
            //创建以日期为名字的文件夹
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String dateFolder = "/" + date + "/";
            //拼接文件的相对路径
            String path = dateFolder + getRandomString(15) + "." + fileType;
            //创建文件夹
            new File(rootPath + dateFolder).mkdir();
            //写入文件
            file.transferTo(new File(rootPath + path));
            json.put("state", "SUCCESS");
            json.put("title", file.getName());
            json.put("url", "/upload/eclassbrand/news" + path);// 图片访问 相对路径
            json.put("original", file.getName());
        } catch (Exception e) {
            json.put("state", "ERROR");
            throw new Exception("上传文件失败！");
        }
        out.print(json.toString());
    }

    /**
     * @Title: getRandomString
     * @Description: 随机生成字符串
     * @param @param length 字符串长度
     * @return String
     * @date createTime：2018年4月9日下午1:45:52
     */
    public static String getRandomString(int length){
        //产生随机数
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        //循环length次
        for(int i=0; i<length; i++){
            //产生0-2个随机数，既与a-z，A-Z，0-9三种可能
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                //如果number产生的是数字0；
                case 0:
                    //产生A-Z的ASCII码
                    result=Math.round(Math.random()*25+65);
                    //将ASCII码转换成字符
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    //产生a-z的ASCII码
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    //产生0-9的数字
                    sb.append(String.valueOf
                            (new Random().nextInt(10)));
                    break;
            }
        }
        return sb.toString();
    }



    /**
     * resizeImage:(等比例压缩图片文件大小)
     * is  文件输入流
     * os  字节数组输出流在内存中创建一个字节数组缓冲区，所有发送到输出流的数据保存在该字节数组缓冲区中
     * size 新图片宽度
     * format 新图片格式
     */
    public static byte[] resizeImage(InputStream is,ByteArrayOutputStream os,int size,String format) throws IOException{
        BufferedImage prevImage = ImageIO.read(is);
        double width = prevImage.getWidth();
        double height = prevImage.getHeight();
        double percent = size/width;
        int newWidth = (int)(width*percent);
        int newHeight = (int)(height*percent);
        BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, newWidth,newHeight,null);
        ImageIO.write(image, format, os);
        is.close();
        return os.toByteArray();
    }
}
